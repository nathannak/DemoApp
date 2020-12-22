package com.demo.demoapplication.testhelper

import com.demo.demoapplication.testhelper.helper.EmptyBodyResponseHelper
import com.demo.demoapplication.testhelper.helper.OKReponseTestHelper
import com.demo.demoapplication.testhelper.helper.UnauthorizedResponseHelper
import com.demo.demoapplication.util.ServerResponseAnalyzer
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

/*
Test class to see if ServerResponseAnalyzer class is able to detect:
1- Unauthorized response [should return true]
2- Null response [happens rarely, but it can happen, should return true]
2- OK response [should return false]
3- Empty body response [should return false]

Written by  N on 12/13/20
*/

class ServerResponseAnalyzerTestClass () {

    var oKReponseTestHelper = OKReponseTestHelper()
    var emptyBodyResponseHelper = EmptyBodyResponseHelper()
    var UnauthorizedResponseHelper = UnauthorizedResponseHelper()

    @Before
    fun setup(){

        //make calls simultaneously
        val job = CoroutineScope(Dispatchers.IO).launch {
            UnauthorizedResponseHelper.simulateUnauthorizedResponseUsingRetrofit()
            oKReponseTestHelper.simulateOKResponseUsingRetrofit("ABC")
            emptyBodyResponseHelper.simulateEmptyBodyUsingRetrofit()
        }

        runBlocking {
            job.join()
        }

    }

    @Test
    fun ServerResponseAnalyzer_Unauthorized() {

        Assert.assertTrue(ServerResponseAnalyzer(UnauthorizedResponseHelper.badResponse).errorReturned())
    }

    @Test
    fun ServerResponseAnalyzer_NullResponse() {

        UnauthorizedResponseHelper.badResponse=null
        Assert.assertTrue(ServerResponseAnalyzer(UnauthorizedResponseHelper.badResponse).errorReturned())
    }

    @Test
    fun ServerResponseAnalyzer_OKResponse() {

        Assert.assertFalse(ServerResponseAnalyzer(oKReponseTestHelper.okResponse).errorReturned())
    }

    @Test
    fun ServerResponseAnalyzer_EmptyBody() {

        Assert.assertFalse(ServerResponseAnalyzer(emptyBodyResponseHelper.okResponse).errorReturned())
    }

}