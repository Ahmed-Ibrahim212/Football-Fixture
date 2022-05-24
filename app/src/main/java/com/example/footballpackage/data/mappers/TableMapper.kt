package com.example.footballfixtures.data.mappers

import com.example.footballpackage.data.remote.dto.Table
import com.example.footballpackage.data.remote.dto.Team

class TableDomainMapper(tableResponse: List<Table>?, competitionId: Int?) {
    val tableDomain = tableResponse?.map { tableResponse ->
        val teamDomain = Team(
            competitionId = competitionId,
            id = tableResponse.team.id,
            name = tableResponse.team.name,
            crestUrl = tableResponse.team.crestUrl,
            shortName = tableResponse.team.shortName,
            tla = tableResponse.team.tla
        )
        Table(
            competitionId = competitionId,
            id = tableResponse.id,
            position = tableResponse.position,
            team = teamDomain,
            playedGames = tableResponse.playedGames,
            won = tableResponse.won,
            draw = tableResponse.draw,
            lost = tableResponse.lost,
            points = tableResponse.points,
            goalsFor = tableResponse.goalsFor,
            goalsAgainst = tableResponse.goalsAgainst,
            goalDifference = tableResponse.goalDifference
        )
    }
}