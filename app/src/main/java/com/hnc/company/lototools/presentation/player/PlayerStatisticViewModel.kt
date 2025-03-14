package com.hnc.company.lototools.presentation.player

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.domain.entity.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
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
            Transaction(
                date = getTodayDate(),
                playerId = 4,
                playType = "Lô",
                number = "36",
                profit = -50000.0,
                name = "SonNM"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 1,
                playType = "Lô",
                number = "51",
                profit = -50000.0,
                name = "HuyNC"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 2,
                playType = "Đuôi 2",
                number = "2 7",
                profit = -50000.0,
                name = "HaiTV"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 3,
                playType = "Lô",
                number = "",
                profit = -50000.0,
                name = "LongVN"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 8,
                playType = "Lô",
                number = "00",
                profit = -50000.0,
                name = "CuongTV"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 6,
                playType = "Lô",
                number = "82",
                profit = -50000.0,
                name = "ChinhNQ"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 5,
                playType = "Lô",
                number = "7",
                profit = -50000.0,
                name = "KienPD"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 7,
                playType = "Lô",
                number = "",
                profit = -50000.0,
                name = "DungDT"
            ),
            Transaction(
                date = getTodayDate(),
                playerId = 9,
                playType = "Lô",
                number = "12",
                profit = -50000.0,
                name = "CuongNV"
            )
        )
        val transactionCollection = firebaseDatabase.collection("transactions")

        for (transaction in transactions) {
            transactionCollection
                .whereEqualTo("playerId", transaction.playerId)
                .whereEqualTo("date", transaction.date)
                .get()
                .addOnSuccessListener { snapshot ->
                    if (!snapshot.isEmpty) {
                        // Nếu tồn tại, ghi đè dữ liệu cũ
                        for (doc in snapshot.documents) {
                            doc.reference.set(transaction, SetOptions.merge())
                                .addOnSuccessListener { println("Updated transaction for playerId ${transaction.playerId}") }
                                .addOnFailureListener { e -> println("Error updating: $e") }
                        }
                    } else {
                        // Nếu không có, tạo mới document
                        transactionCollection.add(transaction)
                            .addOnSuccessListener { println("Added new transaction for playerId ${transaction.playerId}") }
                            .addOnFailureListener { e -> println("Error adding: $e") }
                    }
                }
                .addOnFailureListener { e -> println("Error checking existing transactions: $e") }
        }
    }

    fun getTodayDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(Date()) // Trả về ngày hôm nay dạng getTodayDate()
    }

}
