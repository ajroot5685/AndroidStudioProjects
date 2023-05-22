package com.example.myfbdb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfbdb.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyProductAdapter
    lateinit var rdb:DatabaseReference
    var findQuery = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init(){
        layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false)
        rdb = Firebase.database.getReference("Products/items")
        val query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Product>()
            .setQuery(query, Product::class.java)
            .build()
        adapter = MyProductAdapter(option)
        adapter.itemClickListener = object:MyProductAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int) {
                binding.apply {
                    pIdEdit.setText(adapter.getItem(position).pId.toString())
                    pNameEdit.setText(adapter.getItem(position).pName)
                    pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
                }
            }
        }
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            insertbtn.setOnClickListener {
                initQuery()
                val item = Product(pIdEdit.text.toString().toInt(), pNameEdit.text.toString(), pQuantityEdit.text.toString().toInt())
                rdb.child(pIdEdit.text.toString()).setValue(item)
                clearInput()
            }

            findbtn.setOnClickListener {
                if(!findQuery)
                    findQuery = true
                if(adapter!=null)
                    adapter.stopListening()
                val query = rdb.orderByChild("pname").equalTo(pNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(query, Product::class.java)
                    .build()
                adapter = MyProductAdapter(option)
                adapter.itemClickListener = object:MyProductAdapter.OnItemClickListener{
                    override fun OnItemClick(position: Int) {
                        binding.apply {
                            pIdEdit.setText(adapter.getItem(position).pId.toString())
                            pNameEdit.setText(adapter.getItem(position).pName)
                            pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
                        }
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

            deletebtn.setOnClickListener {
                initQuery()
                rdb.child(pIdEdit.text.toString()).removeValue()
                clearInput()
            }

            updatebtn.setOnClickListener {
                initQuery()
                rdb.child(pIdEdit.text.toString())
                    .child("pquantity")
                    .setValue(pQuantityEdit.text.toString().toInt())
                clearInput()
            }
        }
    }

    // find 기능은 질의유형을 바꾸어서 작업을 수행하게 된다. 따라서 다른 버튼 init, delete, update에서 바껴진 질의유형을 원래대로 돌리기 위한 함수이다.
    fun initQuery(){
        if(findQuery) {
            findQuery = false
            if (adapter != null)
                adapter.stopListening()
            val query = rdb.limitToLast(50)
            val option = FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product::class.java)
                .build()
            adapter = MyProductAdapter(option)
            adapter.itemClickListener = object : MyProductAdapter.OnItemClickListener {
                override fun OnItemClick(position: Int) {
                    binding.apply {
                        pIdEdit.setText(adapter.getItem(position).pId.toString())
                        pNameEdit.setText(adapter.getItem(position).pName)
                        pQuantityEdit.setText(adapter.getItem(position).pQuantity.toString())
                    }
                }
            }
            binding.recyclerView.adapter = adapter
            adapter.startListening()
        }
    }

    fun clearInput(){
        binding.apply {
            pIdEdit.text.clear()
            pNameEdit.text.clear()
            pQuantityEdit.text.clear()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}