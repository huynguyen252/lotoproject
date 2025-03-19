package com.hnc.company.lototools.base.mvi

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavController
import com.hnc.company.lototools.base.composetheme.dialog.ProgressBarDialog
import com.hnc.company.lototools.base.composetheme.dialog.ShowDialogMessage
import com.hnc.company.lototools.base.composetheme.dialog.TypeDialog
import com.hnc.company.lototools.utils.to
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@Composable
fun BaseScreen(
    navigation: NavController? = null,
    viewModel: BaseViewModel,
    initData: (suspend () -> Unit)? = null,
    effectHandler: @Composable () -> Unit = {
        HandleEffect(navigation, viewModel)
    },
    stateHandler: @Composable (Error?, Boolean?) -> Unit = { error, loading ->
        val showProgress = remember(loading) {
            mutableStateOf(true == loading)
        }
        val showError = remember(error) {
            mutableStateOf(error != null)
        }
        showError.value = error != null
        HandleReceiveState(showError, showProgress, error) {
            viewModel.setError(null)
        }
    },
    handleContent: @Composable (MviState?) -> Unit
) {
    effectHandler.invoke()
    LaunchedEffect(true) {
        initData?.invoke()
    }
    val state = viewModel.mviState.collectAsStateWithLifecycle(null)
    val uiState = state.value
    val content = uiState?.first
    val error = uiState?.second
    val loading = uiState?.third
    stateHandler.invoke(error, loading)

    handleContent.invoke(content)
}

@Composable
fun rememberLifecycleEvent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun HandleReceiveState(
    showError: MutableState<Boolean>,
    showProgress: MutableState<Boolean>,
    error: Error?,
    onDismissError: () -> Unit
) {
    if (showError.value) {
        val errorMsg = error?.message
        if (!errorMsg.isNullOrEmpty()) {
            ShowDialogMessage(errorMsg, showError, TypeDialog.ERROR, {})
        }
    } else {
        onDismissError.invoke()
    }

    if (showProgress.value) {
        ProgressBarDialog(showProgress)
    }
}

@Composable
fun HandleEffect(
    navigation: NavController? = null,
    viewModel: BaseViewModel
) {
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current

    val lifecycleEvent = rememberLifecycleEvent()
    LaunchedEffect(lifecycleEvent) {
        viewModel.mviEffect
            .onEach {
                when (it) {
                    is NavigateTo -> {
                        if (it.popBackStack) {
                            navigation?.popBackStack()
                        }
                        navigation?.to(
                            deeplink = it.deeplink,
                            destination = it.destination,
                            bundle = it.bundle
                        )
                    }
                }
            }
            .flowWithLifecycle(lifecycleOwner.lifecycle)
            .launchIn(viewModel.viewModelScopeExceptionHandler)
    }
}
