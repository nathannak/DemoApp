package com.demo.demoapplication.repository

import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.Acronym
import kotlinx.coroutines.*
import retrofit2.Response
import javax.inject.Inject

//Inject retrofit via constructor
class Repository  @Inject constructor (val repository: RepositoryDependencies) {

    var response : Response<Acronym>? = null
    var job : Job? = null

    fun fetchFromRemote(query:String) {

            job = CoroutineScope(Dispatchers.IO).launch {
                fetchDataUsingRetrofit(query)
        }

        runBlocking {
            /*
            wait for job to finish
            so we can use response inside viewmodel
            i am certain job is not null at this point
            */
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