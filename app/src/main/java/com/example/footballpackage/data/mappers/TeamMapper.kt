package com.example.footballpackage.data.mappers

import com.example.footballpackage.data.remote.dto.Team

class TeamDomainMapper(teamResponse: List<Team>?, competitionId: Int?) {
    val teamDomain = teamResponse?.map { teamResponse ->
        Team(
            competitionId = competitionId,
            id = teamResponse.id,
            name = teamResponse.name,
            crestUrl = teamResponse.crestUrl,
            shortName = teamResponse.shortName,
            tla = teamResponse.tla
        )
    }
}