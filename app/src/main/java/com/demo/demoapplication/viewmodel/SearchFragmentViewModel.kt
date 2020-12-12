package com.demo.demoapplication.viewmodel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.demo.demoapplication.model.AcronymItem
import com.demo.demoapplication.di.RepositoryDependencies
import com.demo.demoapplication.repository.Repository
import kotlinx.coroutines.*

class SearchFragmentViewModel @ViewModelInject constructor(
//    val repository: RepositoryDependencies,
    val repository : Repository,
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

//    fun provideStringFromVM(): String {
//        return repository.provideString()
//    }

    fun getAcronymsFromRepository(query: String): Unit {
        repository.fetchfromRemote(query)
    }

}
