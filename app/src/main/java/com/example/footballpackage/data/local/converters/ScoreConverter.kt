package com.example.footballpackage.data.local.converters

import androidx.room.TypeConverter
import com.example.footballpackage.data.remote.dto.Score
import com.google.gson.Gson

class ScoreConverter {
    companion object {

        @TypeConverter
        @JvmStatic
        fun fromStringToScore(value: String?): Score? {
            return if (value == null) null else Gson().fromJson(value, Score::class.java)
        }

        @TypeConverter
        @JvmStatic
        fun fromScoreToString(score: Score?): String? {
            return Gson().toJson(score)
        }
    }
}