package com.hnc.company.lototools.domain.entity

data class Player(
    val playerId: Int,
    val name: String,
    val avatar: String,
    val amount: Double,
    val history: List<GameHistory>
)

data class GameType(
    val gameTypeId: Int,
    val name: String,
    val profitRate: Float
)

data class GameHistory(
    val historyId: Int,
    val player: Player,
    val gameType: GameType,
    val selectedNumbers: String,
    val playDate: String,
    val isWinner: Boolean,
    val betAmount: Double,
    val winnings: Double
)