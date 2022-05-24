package com.example.footballpackage.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Squad(
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("position") val position: String?,
    @SerializedName("teamId") var teamId: Int?
)

data class SquadResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("squad") val squad: List<Squad>?
)