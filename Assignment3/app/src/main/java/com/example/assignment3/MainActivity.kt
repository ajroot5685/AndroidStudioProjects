package com.example.assignment3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        val fragment = supportFragmentManager.beginTransaction()
        val listFragment = ListFragment()
        fragment.replace(R.id.frameLayout, listFragment)
        fragment.commit()
    }
}