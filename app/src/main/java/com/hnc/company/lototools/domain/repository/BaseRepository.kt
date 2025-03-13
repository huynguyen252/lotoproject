package com.hnc.company.lototools.domain.repository

import com.hnc.company.lototools.data.request.GetResultRequest
import com.hnc.company.lototools.domain.entity.ResultModel

interface BaseRepository {
    suspend fun getResult(value: GetResultRequest?): Result<ResultModel>
}
