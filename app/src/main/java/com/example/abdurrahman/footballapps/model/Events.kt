package com.example.abdurrahman.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Events(

        val id: Long?,

        @SerializedName("idEvent")
        var idEvent: String? = null,

        @SerializedName("idHomeTeam")
        var idHomeTeam: String? = null,

        @SerializedName("idAwayTeam")
        var idAwayTeam: String? = null,

        @SerializedName("strHomeTeam")
        var teamHomeName: String? = null,

        @SerializedName("strAwayTeam")
        var teamAwayName: String? = null,

        @SerializedName("intHomeScore")
        var teamHomeScore: String? = null,

        @SerializedName("intAwayScore")
        var teamAwayScore: String? = null,

        @SerializedName("strHomeGoalDetails")
        var strHomeGoalDetails: String? = null,

        @SerializedName("strAwayGoalDetails")
        var strAwayGoalDetails: String? = null,

        @SerializedName("intHomeShots")
        var intHomeShots: String? = null,

        @SerializedName("intAwayShots")
        var intAwayShots: String? = null,

        @SerializedName("strHomeLineupGoalkeeper")
        var strHomeLineupGoalkeeper: String? = null,

        @SerializedName("strAwayLineupGoalkeeper")
        var strAwayLineupGoalkeeper: String? = null,

        @SerializedName("strHomeLineupDefense")
        var strHomeLineupDefense: String? = null,

        @SerializedName("strAwayLineupDefense")
        var strAwayLineupDefense: String? = null,

        @SerializedName("strHomeLineupMidfield")
        var strHomeLineupMidfield: String? = null,

        @SerializedName("strAwayLineupMidfield")
        var strAwayLineupMidfield: String? = null,

        @SerializedName("strHomeLineupForward")
        var strHomeLineupForward: String? = null,

        @SerializedName("strAwayLineupForward")
        var strAwayLineupForward: String? = null,

        @SerializedName("strHomeLineupSubstitutes")
        var strHomeLineupSubstitutes: String? = null,

        @SerializedName("strAwayLineupSubstitutes")
        var strAwayLineupSubstitutes: String? = null,

        @SerializedName("strHomeFormation")
        var strHomeFormation: String? = null,

        @SerializedName("strAwayFormation")
        var strAwayFormation: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,

        @SerializedName("dateEvent")
        var dateEvent: String? = null
) : Parcelable