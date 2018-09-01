package com.example.abdurrahman.footballapps.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teams(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strDescriptionEN")
        var strDesc: String? = null,

        @SerializedName("intFormedYear")
        var intYear: String? = null,

        @SerializedName("strStadium")
        var strStadium: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
): Parcelable