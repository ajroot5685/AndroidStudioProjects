package com.example.test1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.test1.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val item:ArrayList<MyItem> = ArrayList()
    lateinit var adapter: MyItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initInputButton()
        init()
    }

    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,false)
        adapter= MyItemAdapter(item)
        adapter.itemClickListener=object :MyItemAdapter.OnItemClickListener{
            override fun onItemClick(data: MyItem, position: Int) {

            }
        }
        binding.recyclerView.adapter=adapter
    }

    fun initInputButton() {
        binding.inputButton.setOnClickListener{
            val inputName=binding.itemNameLayout1.editText?.text.toString()
            val inputCompany=binding.itemNameLayout2.editText?.text.toString()
            val inputCell=binding.itemNameLayout3.editText?.text.toString()

            // 전에 작성했던 데이터들 지우기
            binding.itemName1.setText(null)
            binding.itemName2.setText(null)
            binding.itemName3.setText(null)

            item.add(MyItem(inputName, inputCompany, inputCell))
            adapter.notifyItemInserted(item.size)
            Toast.makeText(this,"상품이 등록되었습니다!", Toast.LENGTH_SHORT).show()
        }
    }

    fun init(){

    }
}