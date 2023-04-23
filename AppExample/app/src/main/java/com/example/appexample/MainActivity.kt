package com.example.appexample

import android.app.AlertDialog.Builder
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.appexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button.setOnClickListener {
            getAppListAction()
        }
    }

    val requestPermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){
            Toast.makeText(this,"권한이 승인되었습니다.",Toast.LENGTH_SHORT).show()
            getAppListAction()
        }else{
            Toast.makeText(this,"권한 승인이 거부되었습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    private fun appListAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 앱 리스트 접근 권한이 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                    _,_->
                requestPermissionLauncher.launch("com.example.applists")
            }.setNegativeButton("Cancel"){
                    dlg,_-> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }

    private fun getAppListAction() {
        val intent=Intent("com.example.applist")

        when{
            ActivityCompat.checkSelfPermission(this, "com.example.applists")==
                    PackageManager.PERMISSION_GRANTED ->{
                        startActivity(intent)
                    }

            ActivityCompat.shouldShowRequestPermissionRationale(this, "com.example.applists")->{
                appListAlertDlg()
            }

            else->{
                requestPermissionLauncher.launch("com.example.applists")
            }
        }
    }


}