package com.example.abdurrahman.footballapps.ui.match.searchmatch

import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.EventResponse
import com.example.abdurrahman.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class SearchMatchPresenter(private val view: SearchMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val contextProvider: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getSearchMatchList(teamName: String?) {
        view.showLoading()
        async(contextProvider.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchMatch(teamName)),
                        EventResponse::class.java)
            }
            view.showListMatch(data.await().event)
            view.hideLoading()
        }
    }
}