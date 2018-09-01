package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import com.example.abdurrahman.footballapps.model.Player

interface PlayersView {
    fun showLoading()
    fun hideLoading()
    fun showPlayerList(player: List<Player>)
}