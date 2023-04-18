package com.example.lecmp3

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lecmp3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var  binding: ActivityMainBinding
    var mediaPlayer: MediaPlayer? = null
    var vol = 0.5f
    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    override fun onResume() {
        super.onResume()
        if(flag)
            mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    fun initLayout(){
        // binding.imageView
        binding.apply {
            imageView.setVolumeListener(object :VolumeControlView.VolumeListener{
                override fun onChanged(angle: Float) {
                    vol = if (angle > 0) {    // 0 ~ 180 -> 0 ~ 0.5
                        angle / 360
                    } else {    // -180 ~ 0 -> 0.5 ~ 1
                        (360 + angle) / 360
                    }
                    mediaPlayer?.setVolume(vol, vol)
                }

            })

            playBtn.setOnClickListener{
                if(mediaPlayer==null){
                    mediaPlayer=MediaPlayer.create(this@MainActivity, R.raw.christian)
                    mediaPlayer?.setVolume(vol, vol)
                }
                mediaPlayer?.start()
                flag = true
            }
            pauseBtn.setOnClickListener{
                mediaPlayer?.pause()
                flag = false
            }
            stopBtn.setOnClickListener {
                mediaPlayer?.stop()     // a?.b 문법 a가 null이 아니면 b를 실행
                mediaPlayer?.release()
                mediaPlayer = null
                flag = false
            }
        }
    }
}