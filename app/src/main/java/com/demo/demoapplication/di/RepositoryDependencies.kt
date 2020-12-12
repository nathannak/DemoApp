package com.demo.demoapplication.di

import android.content.Context
import com.demo.demoapplication.model.AcronymApi
import com.demo.demoapplication.util.constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryDependencies @Inject constructor() {

    fun provideString() = "I was injected from Repo->VM->here!"

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideAcronymService(): AcronymApi {
        return Retrofit.Builder()
            .baseUrl(constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AcronymApi::class.java)
    }

}