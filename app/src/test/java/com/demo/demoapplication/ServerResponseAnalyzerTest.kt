package com.demo.demoapplication

import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.model.AcronymApi
import com.demo.demoapplication.util.Constants
import com.demo.demoapplication.util.ServerResponseAnalyzer
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/*
Test class to see if ServerResponseAnalyzer class is able to detect some of different HTTPS responses
*/

class ServerResponseAnalyzerTest () {

    var badResponse : Response<Acronym>? = null
    var okResponse  : Response<Acronym>? = null

    @Before
    fun setup(){

        //make calls simoultneously for OK and BAD reponse
        val job = CoroutineScope(Dispatchers.IO).launch {
            simulateUnauthorizedResponseUsingRetrofit()
            simulateOKResponseUsingRetrofit("ABC")

        }
        runBlocking {
            job.join()
        }

    }

    @Test
    fun ServerResponseAnalyzer_Unauthorized() {

        Assert.assertTrue(ServerResponseAnalyzer(badResponse).errorReturned())
    }

    @Test
    fun ServerResponseAnalyzer_NullResponse() {

        badResponse=null
        Assert.assertTrue(ServerResponseAnalyzer(badResponse).errorReturned())
    }

    @Test
    fun ServerResponseAnalyzer_OKResponse() {

        badResponse=null
        Assert.assertFalse(ServerResponseAnalyzer(okResponse).errorReturned())
    }

    /*
    HELPER FUNCTIONS FOR UNAUTHORIZED RESPONSE
    */
    suspend fun simulateUnauthorizedResponseUsingRetrofit() {
        CoroutineScope(Dispatchers.IO).async {
            badResponse = provideUnauthorizedServiceTest().getAcronym()
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
        suspend fun getAcronym(): Response<Acronym>
    }

    /*
    HELPER FUNCTIONS FOR OK Response
    */
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