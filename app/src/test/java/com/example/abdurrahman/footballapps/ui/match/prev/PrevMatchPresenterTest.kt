package com.example.abdurrahman.footballapps.ui.match.prev

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

class PrevMatchPresenterTest {
    @Mock
    private lateinit var view: PrevMatchView
    @Mock
    private lateinit var gson: Gson
    @Mock
    private lateinit var apiRepository: ApiRepository
    private lateinit var presenter: PrevMatchPresenter

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        presenter = PrevMatchPresenter(view, apiRepository, gson, TestContextProvider())
    }

    @Test
    fun getPrevMatchList() {
        val event: MutableList<Events> = mutableListOf()
        val response = EventsResponse(event)

        `when`(gson.fromJson(apiRepository.doRequest(TheSportDBApi.getPrevMatch()),
                EventsResponse::class.java)).thenReturn(response)

        presenter.getPrevMatchList()

        verify(view).showLoading()
        verify(view).showPrevMatch(event)
        verify(view).hideLoading()
    }
}