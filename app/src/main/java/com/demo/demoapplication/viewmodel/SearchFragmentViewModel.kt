package com.demo.demoapplication.viewmodel

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

    //Inject repository to ViewModel using Hilt
    private val repository : Repository,

    /*
    Injecting state to better handle configuration changes,
    I am not using it, but showing how we can inject SavedStateHandle
    to ViewModel
    */
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    //response retrieved from repo
    var response : Response<Acronym>? = null

    val acronymLiveData : MutableLiveData<Acronym> = MutableLiveData()

    /*
    These variables are meant to hold states, such as empty response, or no results state.
    States can be grouped into a separate class.
    But, i define them as variables here for simplicity.
    */
    val errorLoading : MutableLiveData<Boolean> = MutableLiveData()
    val noResults    : MutableLiveData<Boolean> = MutableLiveData()
    val isLoading    : MutableLiveData<Boolean> = MutableLiveData(false)

    fun getAcronymsFromRepository(query: String){

        /*
        ask repository to get data from network
        let UI to show progressbar while loading
        */
        isLoading.value=true
        repository.fetchFromRemote(query)
        response = repository.response
        isLoading.value=false

        /*
        Inspect response here, because we setup live data in
        ViewModel for ui to observe.
        it's better to wrap Retrofit call with generic api response class,
        however this way we have more  readability and power over what
        to do depending on different HTTP responses, both methods work
        */

        //Analyze the response
        if(ServerResponseAnalyzer(response).errorReturned()){
            errorLoading.value=true
        }

        //Already checked for null response and null body in ServerResponseAnalyzer class
        else if(response?.body()?.size==0){

            //This means no acronyms could be found
            noResults.value=true
        }
        else{
            //At this point we are sure we have a proper response
            acronymLiveData.value = repository.response!!.body()
        }

    }

}
