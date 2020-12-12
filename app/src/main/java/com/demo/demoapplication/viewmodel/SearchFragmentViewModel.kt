package com.demo.demoapplication.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.repository.Repository
import com.demo.demoapplication.util.ServerResponseAnalyzer
import retrofit2.Response

class SearchFragmentViewModel @ViewModelInject constructor(
    //Todo consider private
    //Inject repository to viewmodel, we could also Not do thid, i am just show casing
    val repository : Repository,
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var response : Response<Acronym>? = null
    val acronymLiveData : MutableLiveData<Acronym> = MutableLiveData()
    val errorLoading : MutableLiveData<Boolean> = MutableLiveData()

    fun getAcronymsFromRepository(query: String){

        //ask repository to get data from network
        repository.fetchFromRemote(query)
        response = repository.response
        //sanitize repsonse
        Log.d("apptag", repository.response!!.code().toString())
        Log.d("apptag", repository.response!!.errorBody().toString())
        Log.d("apptag", repository.response!!.message().toString())
        Log.d("apptag", repository.response!!.headers().toString())
        Log.d("apptag", repository.response!!.body().toString())
        Log.d("apptag", repository.response!!.raw().toString())

        /*
        check response, because we setup live data here for ui to observe
        it's better to wrap retrpfit call with generic spi response class
        but this was we hsve more power over what to depending on
        different HTTP responses, both methods work
        */

        if(ServerResponseAnalyzer(response).errorReturned()){
            errorLoading.value=true
        }

        acronymLiveData.value = repository.response!!.body()

    }

}
