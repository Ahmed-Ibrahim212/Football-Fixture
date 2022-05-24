package com.example.footballpackage.data.local.converters

import androidx.room.TypeConverter
import com.example.footballpackage.data.remote.dto.Competition
import com.google.gson.Gson

class CompetitionConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromCompetitionToString(value: String?): Competition? {
            return if (value == null) null else Gson().fromJson(value, Competition::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromStringToCompetition(score: Competition?): String? {
            return Gson().toJson(score)
        }
    }
}