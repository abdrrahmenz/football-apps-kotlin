package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.PlayerResponse
import com.example.abdurrahman.footballapps.util.CoroutineContextProvider
import com.google.gson.Gson
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg

class PlayerPresenter(private val view: PlayersView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson,
                      private val context: CoroutineContextProvider = CoroutineContextProvider()){
    fun getPlayerList(idTeam: String?) {
        view.showLoading()
        async(context.main) {
            val data = bg {
                gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(idTeam)),
                        PlayerResponse::class.java)
            }
            view.hideLoading()
            view.showPlayerList(data.await().player)

        }
    }

}