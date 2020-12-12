package com.example.myapplication

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class Repo @Inject constructor() {
//    @Provides
//    @Singleton
    fun provideString() = "I was injected from Repo->VM->here!"

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context
}