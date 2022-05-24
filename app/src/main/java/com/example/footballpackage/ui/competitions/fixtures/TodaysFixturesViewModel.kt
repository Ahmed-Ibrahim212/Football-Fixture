package com.example.footballpackage.ui.competitions.fixtures

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footballpackage.repository.FootballFixturesRepository
import com.example.footballpackage.utils.Resource
import com.example.footballpackage.data.remote.dto.Match
import com.example.footballpackage.data.remote.dto.TodaysFixturesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodaysFixturesViewModel @Inject constructor(private val repository: FootballFixturesRepository): ViewModel() {

    private var _todaysFixtures = MutableLiveData<Resource<TodaysFixturesResponse>>()
    val todaysFixtures: LiveData<Resource<TodaysFixturesResponse>> get() = _todaysFixtures

    private var _savedTodayFixtures = MutableLiveData<Resource<List<Match>>>()
    val savedTodayFixtures: LiveData<Resource<List<Match>>> get() = _savedTodayFixtures

    fun getFixtures(){
        _todaysFixtures.postValue(Resource.Loading(null, "Loading..."))
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _todaysFixtures.postValue(repository.getTodaysFixtures())
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun saveTodayFixtures(matches: List<Match>?) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.saveTodayFixtures(matches)
            } catch (e: Exception) {
                e.message
            }
        }
    }

    fun getTodayFixturesListFromDatabase() {
        _savedTodayFixtures.value = Resource.Loading(null, "Loading....")
        viewModelScope.launch(Dispatchers.IO) {
            repository.getTodayFixturesListFromDatabase().collect {
                _savedTodayFixtures.postValue(it)
            }
        }
    }
}