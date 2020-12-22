package com.demo.demoapplication.repository

import androidx.lifecycle.MutableLiveData
import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.Acronym
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

/*
Repository in charge of making the network call.
If we add a Room DB, it should be added here as well.

Written by W on 12/13/20
*/

//Inject retrofit via constructor
class Repository @Inject constructor(private val repository: RepositoryDependencies) {

    val mutableLiveData : MutableLiveData<Resource<Acronym>> = MutableLiveData()

    suspend fun fetchFromRemote(query: String) : MutableLiveData<Resource<Acronym>> {
        return fetchDataUsingRetrofit(query)
    }

    private suspend fun fetchDataUsingRetrofit(query: String) : MutableLiveData<Resource<Acronym>> {

        val response = repository.provideAcronymService().getAcronym(query)

        if (response.isSuccessful) {
            mutableLiveData.value = Resource.success(response.body())
            return  mutableLiveData
        }else {
            mutableLiveData.value = Resource.error(response.message(), null)
            return  mutableLiveData
        }
    }
}
