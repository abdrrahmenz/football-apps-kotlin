package com.example.abdurrahman.footballapps.ui.team

import com.example.abdurrahman.footballapps.TestContextProvider
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.Teams
import com.example.abdurrahman.footballapps.model.TeamsResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class TeamsPresenterTest {
    @Mock
    private lateinit var view: TeamsView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: TeamsPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getTeamList() {
        val teams: MutableList<Teams> = mutableListOf()
        val response = TeamsResponse(teams)
        var leagueName: String? = null

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getTeams(leagueName)),
                TeamsResponse::class.java)).thenReturn(response)

        leagueName = "English Premier League"
        presenter.getTeamList(leagueName)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }

    @Test
    fun getSearchTeam() {
        val teams: MutableList<Teams> = mutableListOf()
        val response = TeamsResponse(teams)
        var teamName: String? = null

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchTeam(teamName)),
                TeamsResponse::class.java)).thenReturn(response)

        teamName = "Barcelona"
        presenter.getSearchTeam(teamName)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showTeamList(teams)
        Mockito.verify(view).hideLoading()
    }
}