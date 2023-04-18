package com.example.exam21

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.exam21.databinding.ActivityMain2Binding
import com.example.exam21.databinding.ActivityMain3Binding

class MainActivity3 : AppCompatActivity() {
    lateinit var binding: ActivityMain3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    fun init(){
        binding.call.setOnClickListener {
            var tocall=binding.call.text.toString()
            callAction(tocall)
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