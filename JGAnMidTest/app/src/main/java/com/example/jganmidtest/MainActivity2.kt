package com.example.jganmidtest

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.jganmidtest.databinding.ActivityMain2Binding
import com.example.jganmidtest.databinding.ActivityMainBinding
import java.io.PrintStream

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding
    var position:Int=-1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // 받은 data 가져오기
        val intent = intent
        val item = intent.getSerializableExtra("key") as? MyData
        position=intent.getIntExtra("position",-1)

        // 받은 data로 화면에 출력
        if (item != null) {
            binding.name.setText(item.name)
            binding.company.setText(item.company)
            binding.telnumber.setText(item.call)
        }

        init()
    }

    fun init(){
        // 수정하기
        binding.editBtn.setOnClickListener{
            val name=binding.name.text.toString()
            val company = binding.company.text.toString()
            val call = binding.telnumber.text.toString()

            // 만약 다른 파일과 같이 관리하여 데이터가 유지되어야할 때
//            val output = PrintStream(openFileOutput("voc.txt", MODE_APPEND))
//            output.println(name)
//            output.println(company)
//            output.println(call)
//            output.close()
            val i = Intent()
            i.putExtra("voc",MyData(name,company,call))
            i.putExtra("position",position)
            setResult(Activity.RESULT_OK, i)
            finish()
        }
        // 취소
        binding.cancelBtn.setOnClickListener{
            finish()
        }
    }
}