package com.example.footballpackage.data.repository

import com.example.footballpackage.data.FootballAPI
import com.example.footballpackage.data.local.FootballDao
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.Resource
import com.example.footballpackage.utils.apiCall
import com.example.footballpackage.data.remote.dto.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FootballFixturesRepositoryImpl @Inject constructor(
    val dao: FootballDao,
    private val api: FootballAPI
) : FootballFixturesRepository {


    override suspend fun getAllCompetitions(): Resource<CompetitionResponse> {
        return apiCall { api.getAllCompetitions()

        }
    }

    override suspend fun getTableForCompetition(competitionId: Int?): Resource<TableResponse> {
        return apiCall { api.getTableForCompetition(competitionId) }
    }

    override suspend fun getTeamForCompetition(competitionId: Int?): Resource<TeamResponse> {
        return apiCall { api.getTeamForCompetition(competitionId) }
    }

    override suspend fun getFixturesForCompetition(competitionId: Int?): Resource<FixturesResponse> {
        return apiCall { api.getFixturesForCompetition(competitionId) }
    }

    override suspend fun getTodaysFixtures(): Resource<TodaysFixturesResponse> {
        return apiCall { api.getTodaysFixtures() }
    }

    override suspend fun getSquadForTeam(teamId: Int?): Resource<SquadResponse> {
        return apiCall { api.getSquadForTeam(teamId) }
    }

    override suspend fun saveCompetitions(competition: List<Competition>?) {
        dao.saveCompetitions(competition)
    }

    override suspend fun getCompetitionsListFromDatabase(): Flow<Resource<List<Competition>>> {
        return flow {
            dao.getCompetitionsListFromDatabase().collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun deleteAllCompetitions() {
        dao.deleteCompetition()
    }

    override suspend fun saveCompetitionTable(table: List<Table>?) {
        dao.saveCompetitionTable(table)
    }

    override suspend fun getTableListFromDatabase(competitionId: Int?): Flow<Resource<List<Table>>> {
        return flow {
            dao.getTableListFromDatabase(competitionId).collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun saveTeam(team: List<Team>?) {
        dao.saveTeam(team)
    }

    override suspend fun getTeamListFromDatabase(competitionId: Int?): Flow<Resource<List<Team>>> {
        return flow {
            dao.getTeamListFromDatabase(competitionId).collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun saveFixtures(matches: List<Match>?) {
        dao.saveFixtures(matches)
    }

    override suspend fun getFixturesListFromDatabase(competitionId: Int?): Flow<Resource<List<Match>>> {
        return flow {
            dao.getFixturesListFromDatabase(competitionId).collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun saveTodayFixtures(matches: List<Match>?) {
        dao.saveTodayFixtures(matches)
    }

    override suspend fun getTodayFixturesListFromDatabase(): Flow<Resource<List<Match>>> {
        return flow {
            dao.getTodayFixturesListFromDatabase().collect {
                emit(Resource.Success(it))
            }
        }
    }

    override suspend fun saveTeamsSquad(squad: List<Squad>?) {
        dao.saveTeamsSquad(squad)
    }

    override suspend fun getTeamsSquadFromDatabase(teamId: Int?): Flow<Resource<List<Squad>>> {
        return flow {
            dao.getTeamsSquadListFromDatabase(teamId).collect {
                emit(Resource.Success(it))
            }
        }
    }

}