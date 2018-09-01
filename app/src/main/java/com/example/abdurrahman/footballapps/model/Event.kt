package com.example.abdurrahman.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Event (
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

        @SerializedName("dateEvent")
        var dateEvent: String? = null
) : Parcelable