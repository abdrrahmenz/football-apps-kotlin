package com.example.abdurrahman.footballapps.api

import com.example.abdurrahman.footballapps.BuildConfig

object TheSportDBApi {

    private const val SportsApiUrl = BuildConfig.BASE_URL + "api/v1/json/${BuildConfig.TSDB_API_KEY}"

    fun getPrevMatch(league:String?) = "$SportsApiUrl/eventspastleague.php?id=$league"

    fun getNextMatch(league:String?) = "$SportsApiUrl/eventsnextleague.php?id=$league"

    fun getSearchMatch(teamName: String?) = "$SportsApiUrl/searchevents.php?e=$teamName"

    fun getSearchTeam(teamName: String?) = "$SportsApiUrl/searchteams.php?t=$teamName"

    fun getPlayers(idTeams: String?) = "$SportsApiUrl/lookup_all_players.php?id=$idTeams"

    fun getTeamLogo(teamId: String?) = "$SportsApiUrl/lookupteam.php?id=$teamId"

    fun getTeams(league:String?): String {
        return "$SportsApiUrl/search_all_teams.php?l=$league"
    }
}