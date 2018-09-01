package com.example.abdurrahman.footballapps.ui.match.searchmatch

import com.example.abdurrahman.footballapps.TestContextProvider
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.Event
import com.example.abdurrahman.footballapps.model.EventResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {

    @Mock
    private lateinit var view: SearchMatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getSearchMatchList() {
        val event: MutableList<Event> = mutableListOf()
        val response = EventResponse(event)
        var teamName: String? = null

        Mockito.`when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getSearchMatch(teamName)),
                EventResponse::class.java)).thenReturn(response)

        teamName = "Barcelona"
        presenter.getSearchMatchList(teamName)

        Mockito.verify(view).showLoading()
        Mockito.verify(view).showListMatch(event)
        Mockito.verify(view).hideLoading()
    }
}