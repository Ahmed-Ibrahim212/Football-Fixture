package com.example.footballpackage.ui.competitions.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpackage.data.remote.dto.FixturesResponse
import com.example.footballpackage.data.remote.dto.Match
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FixturesViewModel @Inject constructor(private val repository: FootballFixturesRepository): ViewModel() {

    private var _fixtures = MutableLiveData<Resource<FixturesResponse>>()
    val fixtures: LiveData<Resource<FixturesResponse>> get() = _fixtures

    private var _savedFixtures = MutableLiveData<Resource<List<Match>>>()
    val savedFixtures: LiveData<Resource<List<Match>>> get() = _savedFixtures

    fun getFixtures(competitionId: Int?){
        _fixtures.postValue(Resource.Loading(null, "Loading..."))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fixtures.postValue(repository.getFixturesForCompetition(competitionId))
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun saveFixtures(matches: List<Match>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveFixtures(matches)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun getFixturesListFromDatabase(competitionId: Int?) {
        _savedFixtures.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFixturesListFromDatabase(competitionId).collect {
                _savedFixtures.postValue(it)
            }
        }
    }
}