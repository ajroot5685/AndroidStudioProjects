package com.example.gsbsuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gsbsuser.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {
    lateinit var binding:ActivityAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBtn()
    }

    private fun initBtn() {
        binding.apply {
            registerBack.setOnClickListener {
                val i= Intent(this@AccountActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}