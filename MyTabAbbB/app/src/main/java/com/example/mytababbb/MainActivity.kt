package com.example.mytababbb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mytababbb.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    val textarr = arrayListOf<String>("이미지", "리스트", "팀소개")
    val imgarr = arrayListOf<Int>(R.drawable.baseline_agriculture_24,R.drawable.baseline_alt_route_24,R.drawable.baseline_alt_route_24)
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout(){
        binding.viewpager.adapter=MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewpager){
            tab, pos->
            tab.text=textarr[pos]
            tab.setIcon(imgarr[pos])
        }.attach()
    }
}