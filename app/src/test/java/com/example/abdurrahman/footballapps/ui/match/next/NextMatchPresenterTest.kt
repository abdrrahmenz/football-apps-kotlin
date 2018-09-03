package com.example.abdurrahman.footballapps.ui.match.next

import com.example.abdurrahman.footballapps.TestContextProvider
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.api.TheSportDBApi
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.model.EventsResponse
import com.google.gson.Gson
import org.junit.Test

import org.junit.Before
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class NextMatchPresenterTest {

    @Mock
    private lateinit var view: NextMatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository

    private lateinit var presenter: NextMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = NextMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }
    @Test
    fun getNextMatchList() {
        val event: MutableList<Events> = mutableListOf()
        val response = EventsResponse(event)
        var leagueName: String? = null

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getNextMatch(leagueName)),
                EventsResponse::class.java)).thenReturn(response)

        leagueName = "English Premier League"
        presenter.getNextMatchList(leagueName)

        verify(view).showLoading()
        verify(view).showNextMatch(event)
        verify(view).hideLoading()
    }

}