package com.demo.demoapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.demo.demoapplication.R
import dagger.hilt.android.AndroidEntryPoint

/*
App entry point

Written by Nathan N on 12/13/20
*/

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}