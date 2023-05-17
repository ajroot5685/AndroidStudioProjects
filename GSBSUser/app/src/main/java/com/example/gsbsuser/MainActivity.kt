package com.example.gsbsuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gsbsuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBtn()
    }

    private fun initBtn() {
        binding.apply {
            accountBtn.setOnClickListener {
                val i= Intent(this@MainActivity, AccountActivity::class.java)
                startActivity(i)
            }
            communityBtn.setOnClickListener {
                val i= Intent(this@MainActivity, CommunityActivity::class.java)
                startActivity(i)
            }
        }
    }
}