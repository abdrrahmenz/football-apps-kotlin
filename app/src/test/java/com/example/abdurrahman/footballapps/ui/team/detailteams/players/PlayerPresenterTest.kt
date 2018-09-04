package com.example.abdurrahman.footballapps.ui.team.detailteams.players

import com.example.abdurrahman.footballapps.TestContextProvider
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.Player
import com.example.abdurrahman.footballapps.model.PlayerResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class PlayerPresenterTest {

    @Mock
    private lateinit var view: PlayersView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: PlayerPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = PlayerPresenter(view,apiRepository,gson, TestContextProvider())
    }

    @Test
    fun getPlayerList() {
        val player: MutableList<Player> = mutableListOf()
        val response = PlayerResponse(player)
        var teamId : String? = null

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPlayers(teamId)),
                PlayerResponse::class.java)).thenReturn(response)

        teamId = "133739"
        presenter.getPlayerList(teamId)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showPlayerList(player)
        Mockito.verify(view).hideLoading()
    }
}