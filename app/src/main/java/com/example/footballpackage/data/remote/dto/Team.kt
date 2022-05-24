package com.example.footballpackage.data.remote.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "team")
data class Team(
    @SerializedName("competitionId") var competitionId: Int?,
    @SerializedName("id") @PrimaryKey(autoGenerate = false) val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("crestUrl") val crestUrl: String?,
    @SerializedName("shortName") val shortName: String?,
    @SerializedName("tla") val tla: String?
)

data class TeamResponse(
    @SerializedName("competition") val competition: Competition?,
    @SerializedName("teams") val teams: List<Team>?
)

