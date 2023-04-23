package com.example.applist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applist.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var secondbinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        secondbinding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(secondbinding.root)
        initLayout()
    }

    private fun initLayout() {
        secondbinding.button.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }
    }


}