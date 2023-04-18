package com.example.assignment2

import android.content.Intent
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment2.databinding.ActivityItemDetailBinding

class ItemDetail : AppCompatActivity() {
    lateinit var binding:ActivityItemDetailBinding
    lateinit var item:MyItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val item = intent.getSerializableExtra("key") as? MyItem
        if (item != null) {
            binding.name.text = item.name
            binding.description.text = item.description
            binding.category.text = item.category
            binding.count.text = item.count.toString()
        }

        init()
    }

    fun init(){
        binding.button.setOnClickListener{
            finish()
        }
    }
}