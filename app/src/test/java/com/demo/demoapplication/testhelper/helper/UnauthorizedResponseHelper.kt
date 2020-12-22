package com.demo.demoapplication.testhelper.helper

import com.demo.demoapplication.model.Acronym
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/*
HELPER FUNCTIONS FOR TESTING UNAUTHORIZED RESPONSE

Written by  N on 12/13/20
*/

class UnauthorizedResponseHelper {

    var badResponse : Response<Acronym>? = null

    suspend fun simulateUnauthorizedResponseUsingRetrofit() {
        CoroutineScope(Dispatchers.IO).async {
            badResponse = provideUnauthorizedServiceTest().getUnauthorized()
        }.await()
    }

    fun provideUnauthorizedServiceTest(): TestUnauthorizedApi {
        return Retrofit.Builder()
            .baseUrl("http://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TestUnauthorizedApi::class.java)
    }

    interface TestUnauthorizedApi {
        @GET("trending")
        suspend fun getUnauthorized(): Response<Acronym>
    }

}