package com.example.gsbsuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.gsbsuser.databinding.ActivityAccountBinding
import com.example.gsbsuser.databinding.ActivityCommunityBinding

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding
    val myViewModel2: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        initLayout()
//        initBtn()
    }

//    private fun initLayout() {
//        myViewModel2.setLiveData(0)
//        val fragment = supportFragmentManager.beginTransaction()
//        val listFragment = ListFragment()
//        fragment.replace(R.id.frameLayout, listFragment)
//        fragment.commit()
//    }

    private fun initBtn() {

    }
}