package com.example.mydynamicfraglab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.mydynamicfraglab.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val myViewModel: MyViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        myViewModel.setLiveData(0)
        val fragment = supportFragmentManager.beginTransaction()
        val imageFragment = ImageFragment()
        fragment.replace(R.id.frameLayout, imageFragment)
        fragment.commit()
    }
}