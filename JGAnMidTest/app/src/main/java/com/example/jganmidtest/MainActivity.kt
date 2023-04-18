package com.example.jganmidtest

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jganmidtest.databinding.ActivityMainBinding
import java.io.FileNotFoundException
import java.io.PrintStream
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter

    val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if(it.resultCode== Activity.RESULT_OK){ // 전환된 액티비티가 RESULT_OK 응답을 보냈을 때
            val editdata = it.data?.getSerializableExtra("voc") as MyData   // data를 받는다.
            val editposition=it.data!!.getIntExtra("position",0)    // position 정보를 받는다.
            // 기존 data 지우고 수정된 data로 변경
            data.removeAt(editposition)
            data.add(editposition,editdata)
            adapter.notifyItemChanged(editposition)

            Toast.makeText(this,"수정됨", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        initLayout()
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyDataAdapter(data)
        adapter.itemClickListener=object :MyDataAdapter.OnItemClickListener{
            // 이 override 함수에서 onclick 이벤트 발생시 수행할 작업 명시
            override fun onItemClick(data: MyData, position: Int) {
                // data.visible=!data.visible
                // adapter.notifyitemchanged(position)
                // 위와 같이 사용가능

                // 다른 activity로 정보 넘기기
                val intent = Intent(this@MainActivity, MainActivity2::class.java)
                intent.putExtra("key",data)
                intent.putExtra("position",position)
                launcher.launch(intent)
            }
        }
        adapter.itemClickListener2=object :MyDataAdapter.OnItemClickListener{
            // 2번째 onclick 이벤트리스너
            override fun onItemClick(data: MyData, position: Int) {
                var tocall:String=data.call
                callAction(tocall)
                data.count++
                adapter.notifyItemChanged(position)
            }
        }
        binding.recyclerView.adapter = adapter
    }

    fun initLayout(){
        binding.registerBtn.setOnClickListener{
            val name=binding.name.text.toString()
            val company = binding.company.text.toString()
            val telnumber = binding.telnumber.text.toString()
            data.add(MyData(name, company, telnumber))
            adapter.notifyItemInserted(data.size)
        }
    }

    val permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){ // 사용자가 권한 allow를 눌렀을 때
//            callAction()
            // callAction() 함수에 data를 넘겨주어야 해서 승인표시만 함
            Toast.makeText(this,"권한이 승인되었습니다..", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(this,"권한승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun callAction(tocall:String){
        val number = Uri.parse("tel:$tocall")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        when{
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED ->{    // 승인했을 때
                        startActivity(callIntent)
                    }
            ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CALL_PHONE)
                    ->{ // 거부했을 때
                        callAlertDlg()
                    }
            else ->{    // 처음 수행했을 때
                permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }
    }

    fun callAlertDlg(){
        // AVD에서 실행하면 특수한 경우에 작동이 안되는 경우가 있으므로 휴대폰에 연결해서 테스트하는 것을 권장한다.
        val builder= AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE 권한이 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                    _,_-> permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }.setNegativeButton("Cancel"){
                    dlg,_-> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }
}