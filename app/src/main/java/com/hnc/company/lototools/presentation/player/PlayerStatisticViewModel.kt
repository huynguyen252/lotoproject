package com.hnc.company.lototools.presentation.player

import android.util.Log
import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.data.request.GetResultRequest
import com.hnc.company.lototools.domain.entity.GameHistory
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.domain.entity.ResultModel
import com.hnc.company.lototools.domain.usecase.GetResultUseCase
import com.hnc.company.lototools.utils.onEachError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PlayerStatisticViewModel @Inject constructor() : BaseViewModel() {

    private val _players: MutableStateFlow<List<Player>> = MutableStateFlow(emptyList())
    val players = _players.asStateFlow()

    val url = "https://plus.unsplash.com/premium_photo-1664474619075-644dd191935f?q=80&w=2938&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
    
    init {
        fetchData()
    }

    private fun fetchData() {
        _players.value = listOf(
            Player(
                playerId = 1,
                name = "Alice",
                avatar = url,
                amount = 250.0,
                history = listOf()
            ),
            Player(
                playerId = 2,
                name = "Bob",
                avatar = url,
                amount = 320.5,
                history = listOf()
            ),
            Player(
                playerId = 3,
                name = "Charlie",
                avatar = url,
                amount = 150.75,
                history = listOf()
            ),
            Player(
                playerId = 4,
                name = "Diana",
                avatar = url,
                amount = 425.0,
                history = listOf()
            ),
            Player(
                playerId = 5,
                name = "Ethan",
                avatar = url,
                amount = 99.9,
                history = listOf()
            ),
            Player(
                playerId = 6,
                name = "Fiona",
                avatar = url,
                amount = 180.0,
                history = listOf()
            ),
            Player(
                playerId = 7,
                name = "Gabriel",
                avatar = url,
                amount = 300.0,
                history = listOf()
            ),
            Player(
                playerId = 8,
                name = "Hannah",
                avatar = url,
                amount = 560.0,
                history = listOf()
            ),
            Player(
                playerId = 9,
                name = "Ian",
                avatar = url,
                amount = 210.0,
                history = listOf()
            ),
            Player(
                playerId = 10,
                name = "Jade",
                avatar = url,
                amount = 410.0,
                history = listOf()
            ),
            Player(
                playerId = 11,
                name = "Ian",
                avatar = url,
                amount = 210.0,
                history = listOf()
            ),
            Player(
                playerId = 12,
                name = "Jade",
                avatar = url,
                amount = 410.0,
                history = listOf()
            )
        )
    }


}
