package com.hnc.company.lototools.domain.usecase

import com.flowutils.flowFromSuspend
import com.hnc.company.lototools.base.mvi.UseCase
import com.hnc.company.lototools.data.request.GetResultRequest
import com.hnc.company.lototools.domain.entity.ResultModel
import com.hnc.company.lototools.domain.repository.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetResultUseCase @Inject constructor(
    private val repository: BaseRepository
) : UseCase<GetResultRequest?, Result<ResultModel>> {
    override fun invoke(value: GetResultRequest?): Flow<Result<ResultModel>> = flowFromSuspend {
        repository.getResult(value)
    }.flowOn(Dispatchers.IO)

}
