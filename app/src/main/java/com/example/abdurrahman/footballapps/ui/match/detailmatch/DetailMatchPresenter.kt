package com.example.abdurrahman.footballapps.ui.match.detailmatch

import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.TeamsResponse
import com.example.abdurrahman.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class DetailMatchPresenter(private val view: DetailMatchView,
                           private val apiRepository: ApiRepository,
                           private val gson: Gson,
                           private val context: CoroutineContextProvider = CoroutineContextProvider()) {

    fun getHomeBadgeLogo(teamId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamId)),
                        TeamsResponse::class.java)
            }

            view.hideLoading()
            view.showHomeTeamLogo(data.await().teams)
        }
    }

    fun getAwayBadgeLogo(teamId: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeamLogo(teamId)),
                        TeamsResponse::class.java)
            }

            view.hideLoading()
            view.showAwayTeamLogo(data.await().teams)
        }
    }
}