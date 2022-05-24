package com.example.footballpackage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.footballpackage.data.local.converters.*
import com.example.footballpackage.data.remote.dto.*

@Database(
    entities = [Competition::class, Table::class, Team::class, Match::class, Squad::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    TeamConverter::class,
    FixturesConverter::class,
    CompetitionConverter::class,
    ScoreConverter::class,
    RefereeConverter::class,
    DateConverter::class
)
abstract class FootballDatabase : RoomDatabase() {
    abstract val footballFixturesDao: FootballDao
}