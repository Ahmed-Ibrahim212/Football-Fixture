package com.example.footballpackage.data.local.converters

import androidx.room.TypeConverter
import java.util.*

class DateConverter{

    companion object {

        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? {
            return if (value == null) null else Date(value)
        }

        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time
        }
    }

}
