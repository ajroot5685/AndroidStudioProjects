package com.example.voc

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.voc.databinding.ActivityAddVocBinding
import java.io.PrintStream

class AddVocActivity : AppCompatActivity() {
    lateinit var binding:ActivityAddVocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddVocBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun initLayout(){
        binding.addbtn.setOnClickListener{
            val word=binding.word.text.toString()
            val meaning = binding.meaning.text.toString()
            val output = PrintStream(openFileOutput("voc.txt", Context.MODE_APPEND))
            output.println(word)
            output.println(meaning)
            output.close()
            val i = Intent()
            i.putExtra("voc",MyData(word,meaning))
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        binding.cancelbtn.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
    }
}