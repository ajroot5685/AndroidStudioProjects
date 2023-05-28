package com.example.gsbsuser

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.gsbsuser.account.AccountActivity
import com.example.gsbsuser.community.CommunityActivity
import com.example.gsbsuser.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

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
                val currentUser = FirebaseAuth.getInstance().currentUser
                if(currentUser?.isAnonymous==true){ // 익명 사용자일 경우
                    Toast.makeText(this@MainActivity, "익명 로그인의 경우 해당 기능을 이용할 수 없습니다.", Toast.LENGTH_LONG).show()
                }else{
                    val i= Intent(this@MainActivity, AccountActivity::class.java)
                    startActivity(i)
                }
            }
            communityBtn.setOnClickListener {
                val i= Intent(this@MainActivity, CommunityActivity::class.java)
                startActivity(i)
            }
        }
    }
}