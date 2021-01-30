package com.demo.demoapplication.repository

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.room.AcronymEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

/*
Repository in charge of making the network call.
If we add a Room DB, it should be added here as well.

Written by W on 12/13/20
*/

//Inject retrofit via constructor
class Repository @Inject constructor(
    private val repository: RepositoryDependencies,
    @ApplicationContext val context: Context
) {


    suspend fun fetchAcronym(query: String): Flow<Resource<List<AcronymEntity>>> {
        return networkBoundResource(
            query = { repository.provideAcronymDao(context).getAllAcronyms(query) },
            fetch = { repository.provideAcronymService().getAcronym(query) },
            saveFetchResult = {
                insertIntoDB(query,it)
            },
            shouldFetch = {
                 it.isEmpty()
            }
        )
    }

    suspend fun insertIntoDB(query: String, acronym: Acronym) {
        repository.provideAcronymDao(context).addAcronym(AcronymEntity(query, acronym))
    }

}
