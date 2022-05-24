package com.example.footballfixtures.presentation.ui.competitions.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpackage.data.remote.dto.Team
import com.example.footballpackage.data.remote.dto.TeamResponse
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(private val repository: FootballFixturesRepository): ViewModel() {
    private var _team = MutableLiveData<Resource<TeamResponse>>()
    val team: LiveData<Resource<TeamResponse>> get() = _team

    private var _savedTeam = MutableLiveData<Resource<List<Team>>>()
    val savedTeam: LiveData<Resource<List<Team>>> get() = _savedTeam

    fun getCompetitions(competitionId: Int?) {
        _team.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getTeam = repository.getTeamForCompetition(competitionId)
                _team.postValue(getTeam)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun saveTeam(team: List<Team>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveTeam(team)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun getTeamListFromDatabase(competitionId: Int?) {
        _savedTeam.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTeamListFromDatabase(competitionId).collect {
                _savedTeam.postValue(it)
            }
        }
    }
}