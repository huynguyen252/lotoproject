package com.hnc.company.lototools.presentation.detailsplayer

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.domain.entity.Transaction
import com.hnc.company.lototools.utils.convertToInt
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerDetailsViewModel @Inject constructor() : BaseViewModel() {

    private val _transaction: MutableStateFlow<List<Transaction>> = MutableStateFlow(emptyList())
    val transaction = _transaction.asStateFlow()

    private val _player: MutableStateFlow<Player?> = MutableStateFlow(null)
    val player = _player.asStateFlow()

    private val firebaseFireStore = FirebaseFirestore.getInstance()

    fun initData(playerId: String) {
        firebaseFireStore.collection("players")
            .whereEqualTo("playerId", playerId.convertToInt())
            .get()
            .addOnSuccessListener { playerQuery ->
                if (!playerQuery.isEmpty) {
                    val player = playerQuery.documents[0].toObject(Player::class.java)
                    if (player != null) {
                        _player.value = player.copy(amount = 0.0)
                        firebaseFireStore.collection("transactions")
                            .whereEqualTo("playerId", playerId.convertToInt())
                            .get()
                            .addOnSuccessListener { transactionQuery ->
                                val transactions = mutableListOf<Transaction>()
                                for (document in transactionQuery) {
                                    val transaction = document.toObject(Transaction::class.java)
                                    transactions.add(transaction)
                                }
                                _transaction.value = transactions.sortedByDescending { it.date }
                                _player.value =
                                    player.copy(amount = transactions.sumOf { it.profit })
                            }
                            .addOnFailureListener { exception ->
                                Log.e(
                                    "FirestoreError",
                                    "Lỗi khi lấy danh sách transactions",
                                    exception
                                )
                            }
                    } else {
                        Log.w("FirestoreWarning", "Không chuyển đổi được dữ liệu Player")
                    }
                } else {
                    Log.w("FirestoreWarning", "Không tìm thấy Player với playerId: $playerId")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("FirestoreError", "Lỗi khi lấy thông tin Player", exception)
            }
    }
}
