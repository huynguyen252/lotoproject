package com.hnc.company.lototools.presentation.result

import android.util.Log
import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.data.request.GetResultRequest
import com.hnc.company.lototools.domain.entity.ResultModel
import com.hnc.company.lototools.domain.usecase.GetResultUseCase
import com.hnc.company.lototools.utils.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class XSMBResultViewModel @Inject constructor(
    private val getResultUseCase: GetResultUseCase,
) : BaseViewModel() {

    private val _result: MutableStateFlow<ResultModel?> = MutableStateFlow(null)
    val result = _result.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        getResultUseCase.invoke(GetResultRequest(time = "12-03-2025"))
            .onEachError {  }
            .onEach {
                _result.value = it
            }
            .launchIn(viewModelScopeExceptionHandler)
    }

}
