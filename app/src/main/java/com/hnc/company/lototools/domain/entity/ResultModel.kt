package com.hnc.company.lototools.domain.entity

data class ResultModel(
    val countNumbers: Int,
    var time: String? = null,
    val results: Map<String, List<String>>
)
