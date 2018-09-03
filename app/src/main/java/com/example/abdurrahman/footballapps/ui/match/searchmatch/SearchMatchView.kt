package com.example.abdurrahman.footballapps.ui.match.searchmatch

import com.example.abdurrahman.footballapps.model.Event

interface SearchMatchView {
    fun showLoading()
    fun hideLoading()
    fun showListMatch(event: List<Event>)
}