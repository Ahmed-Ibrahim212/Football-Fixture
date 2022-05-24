package com.example.footballpackage.repository

import com.example.footballpackage.utils.Resource
import com.example.footballpackage.data.remote.dto.*
import kotlinx.coroutines.flow.Flow

interface FootballFixturesRepository {
    suspend fun getAllCompetitions(): Resource<CompetitionResponse>

    suspend fun getTableForCompetition(competitionId: Int?): Resource<TableResponse>

    suspend fun getTeamForCompetition(competitionId: Int?): Resource<TeamResponse>

    suspend fun getFixturesForCompetition(competitionId: Int?): Resource<FixturesResponse>

    suspend fun getTodaysFixtures(): Resource<TodaysFixturesResponse>

    suspend fun getSquadForTeam(teamId: Int?): Resource<SquadResponse>

    suspend fun saveCompetitions(competition: List<Competition>?)

    suspend fun getCompetitionsListFromDatabase(): Flow<Resource<List<Competition>>>

    suspend fun deleteAllCompetitions()

    suspend fun saveCompetitionTable(table: List<Table>?)

    suspend fun getTableListFromDatabase(competitionId: Int?): Flow<Resource<List<Table>>>

    suspend fun saveTeam(team: List<Team>?)

    suspend fun getTeamListFromDatabase(competitionId: Int?): Flow<Resource<List<Team>>>

    suspend fun saveFixtures(matches: List<Match>?)

    suspend fun getFixturesListFromDatabase(competitionId: Int?) : Flow<Resource<List<Match>>>

    suspend fun saveTodayFixtures(matches: List<Match>?)

    suspend fun getTodayFixturesListFromDatabase() : Flow<Resource<List<Match>>>

    suspend fun saveTeamsSquad(squad: List<Squad>?)

    suspend fun getTeamsSquadFromDatabase(teamId: Int?) : Flow<Resource<List<Squad>>>
}