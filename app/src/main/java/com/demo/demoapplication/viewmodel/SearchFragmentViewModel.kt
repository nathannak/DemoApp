package com.demo.demoapplication.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.repository.Repository
import com.demo.demoapplication.repository.Resource
import com.demo.demoapplication.util.ServerResponseAnalyzer
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Response

/*
ViewModel class for SearchFragment

Written by W on 12/13/20
*/

class SearchFragmentViewModel @ViewModelInject constructor(

    //Inject repository to ViewModel using Hilt
    private val repository: Repository,
    @Assisted val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var acronymLiveData: MutableLiveData<Resource<Acronym>> = MutableLiveData()

    /*
    These variables are meant to hold states, such as empty response, loading state, and no results state.
    These States can be grouped into a separate class.
    But, i define them as variables here for simplicity in Demo.
    */

    fun getAcronymsFromRepository(query: String) {
        viewModelScope.launch {

            /*
            Ask repository to get data from network,
            let UI to show progressbar while loading
            */

            acronymLiveData.value = Resource.loading(null)
            acronymLiveData.value = repository.fetchFromRemote(query).value
        }
    }
}