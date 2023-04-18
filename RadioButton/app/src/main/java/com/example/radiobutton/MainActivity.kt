package com.example.radiobutton

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.Toast
import com.example.radiobutton.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //init()
        init2()
    }

    var posX:Float=0.0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                posX = event.rawX
            }
            MotionEvent.ACTION_UP ->{
                val distX = posX - event.rawX
                if(distX > 0){
                    Toast.makeText(this, "왼쪽으로 밀었음",
                        Toast.LENGTH_SHORT).show()
                    if(binding.radioButton1.isChecked){
                        binding.radioButton3.isChecked=true
                    }else if(binding.radioButton2.isChecked){
                        binding.radioButton1.isChecked=true
                    }else if(binding.radioButton3.isChecked){
                        binding.radioButton2.isChecked=true
                    }
                }else if(distX <0){
                    Toast.makeText(this, "오른쪽으로 밀었음",
                        Toast.LENGTH_SHORT).show()
                    if(binding.radioButton1.isChecked){
                        binding.radioButton2.isChecked=true
                    }else if(binding.radioButton2.isChecked){
                        binding.radioButton3.isChecked=true
                    }else if(binding.radioButton3.isChecked){
                        binding.radioButton1.isChecked=true
                    }
                }
            }
        }
        return true
    }

    fun init2(){
        binding.radioGroup.setOnCheckedChangeListener{
                radioGroup, checkedID ->
            when (checkedID) {
                R.id.radioButton1 -> binding.imageView.setImageResource(R.drawable.dog1)
                R.id.radioButton2 -> binding.imageView.setImageResource(R.drawable.dog2)
                R.id.radioButton3 -> binding.imageView.setImageResource(R.drawable.dog3)
            }
        }
    }

    fun init(){
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val imageView = findViewById<ImageView>(R.id.imageView)
        radioGroup.setOnCheckedChangeListener{
            radioGroup, checkedID ->
            when (checkedID) {
                R.id.radioButton1 -> imageView.setImageResource(R.drawable.dog1)
                R.id.radioButton2 -> imageView.setImageResource(R.drawable.dog2)
                R.id.radioButton3 -> imageView.setImageResource(R.drawable.dog3)
            }
        }
    }
}