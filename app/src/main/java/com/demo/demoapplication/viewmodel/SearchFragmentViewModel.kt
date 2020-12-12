package com.example.myapplication

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MyViewModel @ViewModelInject constructor(
    val repository: Repo,
    @Assisted  val savedStateHandle: SavedStateHandle
) : ViewModel() {

    fun provideStringFromVM(): String {
        return repository.provideString()
    }

}
