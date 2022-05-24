package com.example.footballpackage.ui.competitions

import androidx.lifecycle.*
import com.example.footballpackage.data.remote.dto.Competition
import com.example.footballpackage.data.remote.dto.CompetitionResponse
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompetitionsViewModel @Inject constructor(private val repository: FootballFixturesRepository) :
    ViewModel() {

    private var _competitions = MutableLiveData<Resource<CompetitionResponse>>()
    val competitions: LiveData<Resource<CompetitionResponse>> get() = _competitions

    private var _savedCompetitions = MutableLiveData<Resource<List<Competition>>>()
    val savedCompetitions: LiveData<Resource<List<Competition>>> get() = _savedCompetitions


    fun getCompetitions() {
        _competitions.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val getCompetitions = repository.getAllCompetitions()
                _competitions.postValue(getCompetitions)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun saveCompetitionToDatabase(competition: List<Competition>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveCompetitions(competition)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun getCompetitionsListFromDatabase() {
        _savedCompetitions.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCompetitionsListFromDatabase().collect {
                _savedCompetitions.postValue(it)
            }
        }
    }
}