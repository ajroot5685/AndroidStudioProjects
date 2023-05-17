package com.example.gsbsuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.gsbsuser.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }



    private fun initLayout(){
        myViewModel.setLiveData(0)
        val fragment = supportFragmentManager.beginTransaction()
        val loginFragment = LoginFragment()
        fragment.replace(R.id.frameLayout, loginFragment)
        fragment.commit()
    }
}