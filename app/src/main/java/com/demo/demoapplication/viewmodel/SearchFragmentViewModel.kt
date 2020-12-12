package com.demo.demoapplication.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.demoapplication.model.AcronymItem
import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.repository.Repository
import kotlinx.coroutines.*
import retrofit2.Response

class SearchFragmentViewModel @ViewModelInject constructor(
//    val repository: RepositoryDependencies,
    val repository : Repository,
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val mLivaData : MutableLiveData<Acronym> = MutableLiveData()

//    fun provideStringFromVM(): String {
//        return repository.provideString()
//    }

    fun getAcronymsFromRepository(query: String){
//        repository.fetchfromRemote(query)
        repository.fetchfromRemote(query)

        //sanitize repsonse

        Log.d("apptag", repository.response!!.code().toString())
        Log.d("apptag", repository.response!!.errorBody().toString())
        Log.d("apptag", repository.response!!.message().toString())
        Log.d("apptag", repository.response!!.headers().toString())
        Log.d("apptag", repository.response!!.body().toString())
        Log.d("apptag", repository.response!!.raw().toString())

//        if(repository.response.) {
//            //no repsonse
//        }
         if (repository.response!!.code()!=200){

        }

        mLivaData.value = repository.response!!.body()
    }

}
