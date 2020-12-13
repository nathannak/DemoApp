package com.demo.demoapplication.util

import com.demo.demoapplication.model.Acronym
import retrofit2.Response

class ServerResponseAnalyzer(val response: Response<Acronym>?) {

    //function has to be either public or internal to be used inside viewmodel; internal is more restrictive
    internal fun errorReturned(): Boolean{
        if(response==null || response.body()==null) return true
        if(response.code()!=200){
            return true
        }

        return false
    }

}