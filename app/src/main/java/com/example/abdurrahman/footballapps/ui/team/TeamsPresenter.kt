package com.example.abdurrahman.footballapps.ui.team

import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.TeamsResponse
import com.example.abdurrahman.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson,
                     private val context: CoroutineContextProvider = CoroutineContextProvider()) {
    fun getTeamList(league: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(league)),
                        TeamsResponse::class.java)
            }
            view.hideLoading()
            view.showTeamList(data.await().teams)

        }
    }

    fun getSearchTeam(teamName: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchTeam(teamName)),
                        TeamsResponse::class.java)
            }
            view.hideLoading()
            view.showTeamList(data.await().teams)
        }
    }
}