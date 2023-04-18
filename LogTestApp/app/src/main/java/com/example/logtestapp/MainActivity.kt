package com.example.logtestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val logTAG = "MSGCHECK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(logTAG, "onCreate")
        setContentView(R.layout.activity_main)
    }

    override fun onStart(){
        super.onStart()
        Log.i(logTAG, "onStart")
    }

    override fun onResume(){
        super.onResume()
        Log.i(logTAG, "onResume")
    }

    override fun onPause(){
        super.onPause()
        Log.i(logTAG, "onPause")
    }

    override fun onStop(){
        super.onStop()
        Log.i(logTAG, "onStop")
    }

    override fun onRestart(){
        super.onRestart()
        Log.i(logTAG, "onRestart")
    }

    override fun onDestroy(){
        super.onDestroy()
        Log.i(logTAG, "onDestroy")
    }
}