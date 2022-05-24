package com.example.footballpackage.data.local.converters

import androidx.room.TypeConverter
import com.example.footballpackage.data.remote.dto.Match
import com.google.gson.Gson

class FixturesConverter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromStringToMatch(value: String?): Match? {
            return if (value == null) null else Gson().fromJson(value, Match::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromMatchToString(match: Match?): String? {
            return Gson().toJson(match)
        }
    }
}