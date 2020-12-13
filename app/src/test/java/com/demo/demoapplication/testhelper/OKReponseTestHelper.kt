package com.demo.demoapplication.testhelper

import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
HELPER FUNCTIONS FOR TESTING OK Response

Written by Nathan N on 12/13/20
*/

class OKReponseTestHelper  {

    var okResponse  : Response<Acronym>? = null

    suspend fun simulateOKResponseUsingRetrofit(query:String) {
    CoroutineScope(Dispatchers.IO).async {
        okResponse = provideAcronymService().getAcronym(query)
    }.await()
}

fun provideAcronymService(): AcronymApi {
    return Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AcronymApi::class.java)
}


interface AcronymApi {
    @GET("http://www.nactem.ac.uk/software/acromine/dictionary.py")
    suspend fun getAcronym(@Query("sf") sf:String): Response<Acronym>
}

}