package com.example.footballpackage.data.mappers

import com.example.footballpackage.data.remote.dto.Competition
import com.example.footballpackage.data.remote.dto.Match
import com.example.footballpackage.data.remote.dto.Score
import com.example.footballpackage.data.remote.dto.Team

class MatchesDomainMapper(match: List<Match>?, competitionId: Int?) {
    val matchDomain = match?.map { match ->
        val homeTeamDomain = Team(
            competitionId = competitionId,
            id = match.homeTeam!!.id,
            name = match.homeTeam.name,
            crestUrl = match.homeTeam.crestUrl,
            shortName = match.homeTeam.shortName,
            tla = match.homeTeam.tla
        )
        val awayTeamDomain = Team(
            competitionId = competitionId,
            id = match.awayTeam!!.id,
            name = match.awayTeam.name,
            crestUrl = match.awayTeam.crestUrl,
            shortName = match.awayTeam.shortName,
            tla = match.awayTeam.tla
        )

       val competitionDomain = match.competition?.let {
           Competition(
               id = it.id,
               name = match.competition.name
           )
       }
        val scoreDomain = match.score?.let {
            Score(
                winner = match.score.winner,
                duration = match.score.duration,
                fullTimeScore = match.score.fullTimeScore,
                halfTimeScore = match.score.halfTimeScore,
                extraTimeScore = match.score.extraTimeScore,
                penaltiesScore = match.score.penaltiesScore
            )
        }
        Match(
            id = match.id,
            competition = competitionDomain,
            competitionId = competitionId,
            utcDate = match.utcDate,
            stage = match.stage,
            status = match.status,
            score = scoreDomain,
            homeTeam = homeTeamDomain,
            awayTeam = awayTeamDomain,
            referees = match.referees
        )
    }
}