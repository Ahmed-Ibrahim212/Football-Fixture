package com.example.footballpackage.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.footballpackage.data.remote.dto.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FootballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCompetitions(competition: List<Competition>?)

    @Query("SELECT * FROM `competition`")
    fun getCompetitionsListFromDatabase(): Flow<List<Competition>>

    @Query("DELETE FROM competition" )
    fun deleteCompetition()


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCompetitionTable(table: List<Table>?)

    @Query("SELECT * FROM `table` WHERE competitionId =:competitionId ORDER BY position ASC")
    fun getTableListFromDatabase(competitionId: Int?): Flow<List<Table>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeam(table: List<Team>?)

    @Query("SELECT * FROM `team` WHERE competitionId=:competitionId ORDER BY name ASC")
    fun getTeamListFromDatabase(competitionId: Int?): Flow<List<Team>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveFixtures(matches: List<Match>?)

    @Query("SELECT * FROM `match` WHERE competitionId=:competitionId  ORDER BY date ASC")
    fun getFixturesListFromDatabase(competitionId: Int?): Flow<List<Match>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTodayFixtures(matches: List<Match>?)

    @Query("SELECT * FROM `match`")
    fun getTodayFixturesListFromDatabase(): Flow<List<Match>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTeamsSquad(matches: List<Squad>?)

    @Query("SELECT * FROM `team` WHERE competitionId=:competitionId  ORDER BY name ASC")
    fun getTeamsSquadListFromDatabase(competitionId: Int?): Flow<List<Squad>>

}