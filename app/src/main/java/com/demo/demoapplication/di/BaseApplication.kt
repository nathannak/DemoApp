package com.demo.demoapplication.di

import android.app.Application
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application()

