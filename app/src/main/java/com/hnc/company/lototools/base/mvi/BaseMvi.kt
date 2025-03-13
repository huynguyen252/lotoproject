package com.hnc.company.lototools.base.mvi

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow

interface BaseMvi {
    val mviIntent: SharedFlow<MviIntent>
    val mviEffect: Flow<MviEffect>
}
