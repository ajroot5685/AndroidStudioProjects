package com.example.mymp3service

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.os.IBinder

class MyService : Service() {
    lateinit var song:String
    var player: MediaPlayer?=null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    var receiver = object: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            playControl(intent)
        }
    }

    override fun onCreate() {
        super.onCreate()
        registerReceiver(receiver, IntentFilter("com.example.MP3SERVICE"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(player!=null && player!!.isPlaying){
            val mainBRIntent = Intent("com.example.MP3ACTIVITY")
            mainBRIntent.putExtra("mode", "playing")
            mainBRIntent.putExtra("song", song)
            mainBRIntent.putExtra("currentPos", player!!.currentPosition)
            mainBRIntent.putExtra("duration", player!!.duration)
            sendBroadcast(mainBRIntent)
        }

        return START_STICKY
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

    private fun playControl(intent: Intent?) {
        val mode = intent!!.getStringExtra("mode")
        if(mode!=null){
            when(mode){
                "play"->{
                    song = intent.getStringExtra("song")!!
                    startPlay()
                }
                "stop"->{
                    stopPlay()
                }
            }
        }
    }

    private fun startPlay(){
        val songid = resources.getIdentifier(song, "raw", packageName)
        if (player != null && player!!.isPlaying) {
            player!!.stop()
            player!!.reset()
            player!!.release()
            player = null
        }

        player = MediaPlayer.create(this, songid)
        player!!.start()

        val mainBRIntent = Intent("com.example.MP3ACTIVITY")
        mainBRIntent.putExtra("mode", "play")
        mainBRIntent.putExtra("duration", player!!.duration)
        sendBroadcast(mainBRIntent)

        player!!.setOnCompletionListener {
            val mainBRIntent = Intent("com.example.MP3ACTIVITY")
            mainBRIntent.putExtra("mode", "stop")
            sendBroadcast(mainBRIntent)

            stopPlay()
        }
    }

    private fun stopPlay(){
        if(player!=null && player!!.isPlaying){
            player!!.stop()
            player!!.reset()
            player!!.release()
            player=null
        }
    }
}