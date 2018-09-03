package com.example.abdurrahman.footballapps.api

import org.junit.Test

import org.mockito.Mockito

class ApiRepositoryTest {

    @Test
    fun doRequest() {
        val apiRepository = Mockito.mock(ApiRepository::class.java)
        val url = "https://www.thesportsdb.com/api/v1/json/1/search_all_teams.php?l=English%20Premier%20League"

        apiRepository.doRequest(url)
        Mockito.verify(apiRepository).doRequest(url)
    }
}