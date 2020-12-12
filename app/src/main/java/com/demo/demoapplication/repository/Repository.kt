package com.demo.demoapplication.repository

import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.Acronym
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

class Repository  @Inject constructor (val repository: RepositoryDependencies) {

    var response : Response<Acronym>? = null
    var job : Job? = null

    fun fetchfromRemote(query:String) {

            job = CoroutineScope(Dispatchers.IO).launch {
                fetchDataUsingRetrofit(query)
        }

        runBlocking {
            job!!.join()
        }

    }

    suspend fun fetchDataUsingRetrofit(query: String) {
        //fetch from network using retrofit
        CoroutineScope(Dispatchers.IO).async {
            response = repository.provideAcronymService().getAcronym(query)
        }.await()
    }

}