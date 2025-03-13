package com.hnc.company.lototools.base.mvi

import kotlinx.coroutines.flow.Flow

interface UseCase<in R, out T> {
    fun invoke(value: R? = null): Flow<T>
}
