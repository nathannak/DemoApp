package com.demo.demoapplication.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AcronymApi {

    @GET("http://www.nactem.ac.uk/software/acromine/dictionary.py")
    suspend fun getAcronym(@Query("sf") sf:String): Response<Acronym>
}
