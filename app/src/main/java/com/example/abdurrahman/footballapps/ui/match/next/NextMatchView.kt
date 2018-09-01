package com.example.abdurrahman.footballapps.ui.match.next

import com.example.abdurrahman.footballapps.model.Events

interface NextMatchView {
    fun showLoading()
    fun hideLoading()
    fun showNextMatch(data: List<Events>)
}