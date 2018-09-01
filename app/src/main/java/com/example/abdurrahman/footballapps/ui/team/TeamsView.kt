package com.example.abdurrahman.footballapps.ui.team

import com.example.abdurrahman.footballapps.model.Teams

interface TeamsView {
    fun showLoading()
    fun hideLoading()
    fun showTeamList(teams: List<Teams>)
}