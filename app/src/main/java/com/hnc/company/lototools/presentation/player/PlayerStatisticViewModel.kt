package com.hnc.company.lototools.presentation.player

import com.hnc.company.lototools.base.mvi.BaseViewModel
import com.hnc.company.lototools.domain.entity.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PlayerStatisticViewModel @Inject constructor() : BaseViewModel() {

    private val _players: MutableStateFlow<List<Player>> = MutableStateFlow(emptyList())
    val players = _players.asStateFlow()

    init {
        fetchData()
    }

    private fun fetchData() {
        _players.value = emptyList()
    }


}
