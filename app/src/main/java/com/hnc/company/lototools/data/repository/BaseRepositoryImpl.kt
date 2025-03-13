package com.hnc.company.lototools.data.repository

import android.content.Context
import com.hnc.company.lototools.data.request.GetResultRequest
import com.hnc.company.lototools.domain.entity.ResultModel
import com.hnc.company.lototools.domain.repository.BaseRepository
import com.hnc.company.lototools.utils.parseHtml
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject


class BaseRepositoryImpl @Inject constructor(private val client: HttpClient) : BaseRepository {
    override suspend fun getResult(value: GetResultRequest?): Result<ResultModel> = runCatching {
        val result = client.get("https://az24.vn/xsmb-${value?.time}.html") {}.bodyAsText()
        parseHtml(result).apply { time = value?.time }
    }
}
