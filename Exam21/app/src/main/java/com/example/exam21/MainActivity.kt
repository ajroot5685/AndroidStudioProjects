package com.example.exam21

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exam21.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter
    var totalprice:Int=0

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){ // 전환된 액티비티가 RESULT_OK 응답을 보냈을 때
            @Suppress("DEPRECATION")
            val adddata=if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
                it.data?.getSerializableExtra("key",MyData::class.java) as MyData
            else
                it.data?.getSerializableExtra("key") as MyData

            data.add(adddata)
            adapter.notifyItemInserted(data.size)

            totalprice+=adddata.price
            binding.totalPrice.text="$totalprice"+"원"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initButton1()
        initButton2()
        initRecyclerView()
    }

    fun init(){
        binding.totalPrice.text="$totalprice"+"원"
    }

    fun initButton1(){
        binding.button1.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            launcher.launch(intent)
        }
    }

    fun initButton2(){
        binding.button2.setOnClickListener {
            val intent = Intent(this@MainActivity, MainActivity3::class.java)
            launcher.launch(intent)
        }
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyDataAdapter(data)
        adapter.itemClickListener=object :MyDataAdapter.OnItemClickListener{
            // 이 override 함수에서 onclick 이벤트 발생시 수행할 작업 명시
            override fun onItemClick(data: MyData, position: Int) {

            }
        }
        binding.recyclerView.adapter=adapter
    }
}