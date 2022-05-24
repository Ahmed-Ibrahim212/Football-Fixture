package com.example.footballpackage.data.local.converters

import androidx.room.TypeConverter
import com.example.footballpackage.data.remote.dto.Team
import com.google.gson.Gson

class TeamConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStringToTeam(value: String?): Team? {
            return if (value == null) null else Gson().fromJson(value, Team::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromTeamToString(score: Team?): String? {
            return Gson().toJson(score)
        }
    }
}