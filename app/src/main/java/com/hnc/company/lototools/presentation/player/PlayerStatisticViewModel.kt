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

            firebaseDatabase.collection("transactions").get().addOnSuccessListener { transactionsSnapshot ->
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
}
