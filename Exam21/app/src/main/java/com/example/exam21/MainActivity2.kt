package com.example.exam21

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam21.databinding.ActivityMain2Binding
import java.io.FileNotFoundException
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {
    lateinit var binding:ActivityMain2Binding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initRecyclerView()
    }

    fun readScanFile(scan:Scanner){
        while(scan.hasNextLine()){
            val name=scan.nextLine()
            val price=scan.nextLine().toInt()
            data.add(MyData(name, price))
        }
    }

    fun initData(){
        // MODE_APPEND 사용 시 파일이 존재한다면 끝부분에 이어서 데이터 추가
        val output = PrintStream(openFileOutput("AJG.txt", Context.MODE_PRIVATE))
        output.println("건국TV")
        output.println("2000")
        output.println("건국냉장고")
        output.println("3000")
        output.println("건국라디오")
        output.println("1000")
        output.close()

        try{
            val scan = Scanner(openFileInput("AJG.txt"))
            readScanFile(scan)
        }catch (e: FileNotFoundException){

        }
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyDataAdapter2(data)
        adapter.itemClickListener=object :MyDataAdapter2.OnItemClickListener{
            // 이 override 함수에서 onclick 이벤트 발생시 수행할 작업 명시
            override fun onItemClick(data: MyData, position: Int) {
                val i=Intent()
                i.putExtra("key",data)
                setResult(Activity.RESULT_OK,i)
                finish()
            }
        }
        binding.recyclerView.adapter=adapter
    }
}