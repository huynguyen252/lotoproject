package com.hnc.company.lototools.domain.entity

data class Player(
    val playerId: Int = 0,
    val name: String = "",
    val avatar: String = "",
    val amount: Double = 0.0
)

data class Transaction(
    val date: String = "",
    val playerId: Int = 0,
    val playType: String= "",
    val number: String= "",
    val profit: Double= 0.0,
    val name: String= ""
)
