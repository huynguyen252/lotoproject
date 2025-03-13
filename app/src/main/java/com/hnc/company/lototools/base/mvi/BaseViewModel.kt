package com.hnc.company.lototools.base.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel(dispatcher: CoroutineDispatcher = Dispatchers.IO) :
    ViewModel(),
    BaseMvi {

    private val _content: MutableStateFlow<MviState> = MutableStateFlow(Initialize)
    val content = _content.asStateFlow()

    private val _error: MutableStateFlow<Error?> = MutableStateFlow(null)
    val error = _error.asStateFlow()

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val loading = _loading.asStateFlow()


    private val _success = Channel<String?>(Channel.BUFFERED)
    val success = _success.receiveAsFlow()

    private val _mviIntent = MutableSharedFlow<MviIntent>(
        0,
        1,
        BufferOverflow.SUSPEND
    )
    final override val mviIntent: SharedFlow<MviIntent>
        get() = _mviIntent.asSharedFlow()

    private val _mviEffect = Channel<MviEffect>(Channel.BUFFERED)
    override val mviEffect: Flow<MviEffect>
        get() = _mviEffect.receiveAsFlow()

    private val exceptionHandler: CoroutineExceptionHandler by lazy {
        getCoroutineExceptionHandler()
    }

    val viewModelScopeExceptionHandler by lazy { viewModelScope + exceptionHandler }

    val mviState = combine(content, error, loading) { f1, f2, f3 ->
        Triple(f1, f2, f3)
    }.stateIn(viewModelScopeExceptionHandler, SharingStarted.WhileSubscribed(), null)

    open fun handleIntents(intent: MviIntent) {}

    init {
        mviIntent.onEach {
            handleIntents(it)
        }.catch {}
            .flowOn(dispatcher)
            .launchIn(viewModelScopeExceptionHandler)
    }

    open fun getCoroutineExceptionHandler(): CoroutineExceptionHandler = CoroutineExceptionHandler {
            _,
            throwable
        ->
        viewModelScope.launch {
            setError(throwable)
        }
    }

    fun setError(throwable: Throwable?) {
        showLoading(false)
        _error.value = Error(throwable?.message)
    }
    fun resetSuccess(){

    }

    fun setSuccess(success: String?){
        _success.trySend(success)
    }

    fun showLoading(isShow: Boolean) {
        _loading.tryEmit(isShow)
    }

    fun setState(state: MviState) {
        _content.value = state
    }

    fun sendIntent(intent: MviIntent) {
        _mviIntent.tryEmit(intent)
    }

    fun sendEffect(effect: MviEffect) {
        viewModelScopeExceptionHandler.launch {
            _mviEffect.send(effect)
        }
    }
}
