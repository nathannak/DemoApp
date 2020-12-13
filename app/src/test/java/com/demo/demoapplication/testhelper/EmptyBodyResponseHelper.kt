package com.demo.demoapplication.testhelper

import com.demo.demoapplication.model.Acronym
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*
HELPER FUNCTIONS FOR EMPTY BODY RESPONSE

Written by Nathan N on 12/13/20
*/

class EmptyBodyResponseHelper {

    var okResponse  : Response<Acronym>? = null

    suspend fun simulateEmptyBodyUsingRetrofit() {
        CoroutineScope(Dispatchers.IO).async {
            okResponse = provideServerEmptyBody().getEmptyBody()
        }.await()
    }

    fun provideServerEmptyBody(): TestEmptyBodyApi {
        return Retrofit.Builder()
            .baseUrl("http://www.nactem.ac.uk/software/acromine/dictionary.py/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestEmptyBodyApi::class.java)
    }

    interface TestEmptyBodyApi {
        @GET("HMMM")
        suspend fun getEmptyBody(): Response<Acronym>
    }

}