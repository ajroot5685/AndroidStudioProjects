package com.example.gsbsuser.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gsbsuser.MainActivity
import com.example.gsbsuser.R
import com.example.gsbsuser.databinding.ActivityCommunityBinding

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding

    var dataList: ArrayList<MyContent> = arrayListOf(
        MyContent("name1","content1","date1","writer1",5,5),
        MyContent("name2","content1","date1","writer1",5,5),
        MyContent("name3","content1","date1","writer1",5,5),
        MyContent("name4","content1","date1","writer1",5,5),
        MyContent("name5","content1","date1","writer1",5,5),
        MyContent("name6","content1","date1","writer1",5,5),
        MyContent("name7","content1","date1","writer1",5,5),
        MyContent("name8","content1","date1","writer1",5,5),
        MyContent("name9","content1","date1","writer1",5,5),
        MyContent("name10","content1","date1","writer1",5,5),
        MyContent("name11","content1","date1","writer1",5,5),
        MyContent("name12","content1","date1","writer1",5,5),
        MyContent("name13","content1","date1","writer1",5,5),
        MyContent("name14","content1","date1","writer1",5,5)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCommunityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBtn()
        initfragment()
    }

    private fun initBtn() {
        binding.apply {
            registerBack.setOnClickListener {
                val i= Intent(this@CommunityActivity, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    private fun initfragment() {
        val fragment = supportFragmentManager.beginTransaction()
        val listFragment = ListFragment()
        fragment.replace(R.id.contentLayout, listFragment)
        fragment.commit()
        intent.putExtra("DataList", dataList)
    }


}