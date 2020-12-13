package com.demo.demoapplication.util

import com.demo.demoapplication.model.Acronym
import retrofit2.Response

/*
This class created to analyze HTTP responses inside ViewModel, so we can hook up our live data variables.
It is better practice to have a call adapter factory, and wrap retrofit object around it.
I am using this methods for simplicity in this Demo app, also it gives me better grip for unit testing.

Written by Nathan N on 12/13/20
*/

class ServerResponseAnalyzer(val response: Response<Acronym>?) {

    //function has to be either public or internal to be used inside ViewModel; internal is more restrictive
    internal fun errorReturned(): Boolean{
        if(response==null || response.body()==null) return true
        if(response.code()!=200){
            return true
        }

        return false
    }

}