package com.example.abdurrahman.footballapps.ui.match.prev

import com.example.abdurrahman.footballapps.model.Events

interface PrevMatchView {

    fun showLoading()
    fun hideLoading()
    fun showPrevMatch(data: List<Events>)
}