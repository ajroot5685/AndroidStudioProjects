package com.example.gsbsuser.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gsbsuser.MainActivity
import com.example.gsbsuser.R
import com.example.gsbsuser.community.board.Board
import com.example.gsbsuser.community.board.BoardFragment
import com.example.gsbsuser.community.comment.CommentFragment
import com.example.gsbsuser.databinding.ActivityCommunityBinding

class CommunityActivity : AppCompatActivity() {
    lateinit var binding: ActivityCommunityBinding

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
        val boardFragment = BoardFragment()
        fragment.replace(R.id.contentLayout, boardFragment)
        fragment.commit()
    }


}