package com.example.abdurrahman.footballapps.ui.match.detailmatch

import com.example.abdurrahman.footballapps.model.Teams

interface DetailMatchView {
    fun showLoading()
    fun hideLoading()
    fun showHomeTeamLogo(data: List<Teams>)
    fun showAwayTeamLogo(data: List<Teams>)
}