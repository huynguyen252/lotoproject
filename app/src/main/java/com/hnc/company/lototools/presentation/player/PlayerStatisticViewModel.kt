package com.hnc.company.lototools.presentation.player

import com.google.firebase.firestore.FirebaseFirestore
import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.domain.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerStatisticViewModel @Inject constructor() : BaseViewModel() {

    private val _players: MutableStateFlow<List<Player>> = MutableStateFlow(emptyList())
    val players = _players.asStateFlow()

    private val firebaseDatabase = FirebaseFirestore.getInstance()

    init {
        fetchPlayersWithProfit()
    }

    private fun fetchPlayersWithProfit() {
        val playersMap = mutableMapOf<Int, Player>()
        val profitMap = mutableMapOf<Int, Double>()

        firebaseDatabase.collection("players").get().addOnSuccessListener { playersSnapshot ->
            for (doc in playersSnapshot.documents) {
                val player = doc.toObject(Player::class.java)
                if (player != null) {
                    playersMap[player.playerId] = player
                }
            }

            firebaseDatabase.collection("transactions").get()
                .addOnSuccessListener { transactionsSnapshot ->
                    for (doc in transactionsSnapshot.documents) {
                        val transaction = doc.toObject(Transaction::class.java)
                        if (transaction != null) {
                            profitMap[transaction.playerId] =
                                (profitMap[transaction.playerId] ?: 0.0) + transaction.profit
                        }
                    }

                    _players.value = playersMap.values.map { player ->
                        player.copy(amount = (profitMap[player.playerId] ?: 0.0))
                    }

                }
        }
    }

    private fun updateData() {
        val transactions = listOf(
            Transaction(date = "14/13/2025", playerId = 4, playType = "Lô", number = "", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 1, playType = "Lô", number = "51", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 2, playType = "Đuôi 2", number = "2 7", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 3, playType = "Lô", number = "", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 8, playType = "Lô", number = "", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 6, playType = "Lô", number = "82", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 5, playType = "Lô", number = "7", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 7, playType = "Lô", number = "", profit = -50000.0),
            Transaction(date = "14/13/2025", playerId = 9, playType = "Lô", number = "12", profit = -50000.0)
        )
        val transactionCollection = firebaseDatabase.collection("transactions")

        transactions.forEach { transaction ->
            transactionCollection.add(transaction)
                .addOnSuccessListener { documentReference ->
                    println("Transaction added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    println("Error adding transaction: $e")
                }
        }
    }
}
