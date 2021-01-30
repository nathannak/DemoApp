package com.demo.demoapplication.di

import android.content.Context
import com.demo.demoapplication.model.AcronymApi
import com.demo.demoapplication.room.AcronymDao
import com.demo.demoapplication.room.AcronymDatabase
import com.demo.demoapplication.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/*
Module to provide Retrofit object to our Repository

Written by W on 12/13/20
*/

@InstallIn(ApplicationComponent::class)
@Module
class RepositoryDependencies @Inject constructor() {

    @Provides
    @Singleton
    fun provideAcronymService(): AcronymApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AcronymApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAcronymDao(@ApplicationContext context: Context): AcronymDao {
        return AcronymDatabase(context).getAcronymDao()
    }

    /* we could also inject network state and context, but probably it's outside of scope of demo

    @Provides
    @Singleton
    fun provideConnectivity(@ApplicationContext context: Context ): CheckConnectivity {
        return CheckConnectivity(context)
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

     */

}