package com.demo.demoapplication.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

//Written by W on 12/13/20

interface AcronymApi {

    @GET("http://www.nactem.ac.uk/software/acromine/dictionary.py")
    suspend fun getAcronym(@Query("sf") sf: String): Acronym
}
