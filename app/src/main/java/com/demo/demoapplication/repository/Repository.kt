package com.demo.demoapplication.repository

import android.util.Log
import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.model.AcronymItem
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository  @Inject constructor (val repository: RepositoryDependencies) {

    fun fetchfromRemote(query:String){

            val job = CoroutineScope(Dispatchers.IO).launch {

            val response = repository.provideAcronymService().getAcronym(query)

            withContext(Dispatchers.Main) {

                if(response.isSuccessful){

                    //since response is successful it's relatively safe to use !!
                    for(ai: AcronymItem in response.body()!!){

                        Log.d("apptag",ai.lfs.get(0).lf)
                    }
                }

//                if (response.isSuccessful) {
//                    countries.value = response.body()
//                    countryLoadError.value = null
//                    loading.value = false
//                } else {
//                    onError("Error: ${response.message()}")
//                }

            }
        }
    }

}