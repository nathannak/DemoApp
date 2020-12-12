package com.demo.demoapplication.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.repository.Repository

class SearchFragmentViewModel @ViewModelInject constructor(
    //Todo consider private
    val repository : Repository,
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val mLivaData : MutableLiveData<Acronym> = MutableLiveData()

    fun getAcronymsFromRepository(query: String){

        //ask repository to get data from network
        repository.fetchFromRemote(query)

        //sanitize repsonse
        Log.d("apptag", repository.response!!.code().toString())
        Log.d("apptag", repository.response!!.errorBody().toString())
        Log.d("apptag", repository.response!!.message().toString())
        Log.d("apptag", repository.response!!.headers().toString())
        Log.d("apptag", repository.response!!.body().toString())
        Log.d("apptag", repository.response!!.raw().toString())

        //sanitize response here, becasue we setup live data here for ui to observe
        //ServerResponseAnalyzer.

        mLivaData.value = repository.response!!.body()
    }

}
