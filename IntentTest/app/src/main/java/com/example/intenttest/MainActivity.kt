package com.example.intenttest

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.intenttest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        checkPermissions()
    }

    // 복수권한 주기 (시작 부분)
    val permissions= arrayOf(android.Manifest.permission.CALL_PHONE, android.Manifest.permission.CAMERA)

    val multiplePermissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
        val resultPermission = it.all {map->
            map.value
        }
        if(!resultPermission){
            //finish() // 앱종료
            Toast.makeText(this,"모든 권한 승인되어야함",Toast.LENGTH_SHORT).show()
        }
    }
    fun checkPermissions(){
        when{
            (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) ->{    // 승인했을 때
                Toast.makeText(this,"모든 권한 승인됨",Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CALL_PHONE)||
                    ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CAMERA)
            ->{ // 거부했을 때
                // callAlertDlg() // 단일 권한 코드
                permissionCheckAlertDlg()
            }
            else ->{    // 처음 수행했을 때
                multiplePermissionLauncher.launch(permissions)
            }
        }
    }


    fun permissionCheckAlertDlg(){
        val builder=AlertDialog.Builder(this)
        builder.setMessage("반드시 CALL_PHONE과 CAMERA 권한이 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                    _,_-> multiplePermissionLauncher.launch(permissions)
            }.setNegativeButton("Cancel"){
                    dlg,_-> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }
    // 복수 권한 주기 (끝부분)

    fun allPermissionGranted() = permissions.all{
        ActivityCompat.checkSelfPermission(this,it)==PackageManager.PERMISSION_GRANTED
    }

    fun callphonePermissionGranted()=ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED

    fun cameraPermissionGranted()=ActivityCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

    // request 후 callback 함수까지 하나의 객체로 처리
    val permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){
        if(it){ // 사용자가 승인했다면
            callAction()
        }else{
            Toast.makeText(this,"권한승인이 거부되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    fun callAlertDlg(){
        val builder=AlertDialog.Builder(this)
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

    fun callAction(){
        val number = Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        if(allPermissionGranted())
            startActivity(callIntent)
        else
            checkPermissions()
    }

    // 단일 권한 코드
//    fun callAction(){
//        val number = Uri.parse("tel:010-1234-1234")
//        val callIntent = Intent(Intent.ACTION_CALL, number)
//        when{
//            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE)
//                    == PackageManager.PERMISSION_GRANTED ->{    // 승인했을 때
//                        startActivity(callIntent)
//                    }
//            ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.CALL_PHONE)
//                    ->{ // 거부했을 때
//                callAlertDlg()
//                    }
//            else ->{    // 처음 수행했을 때
//                permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
//            }
//        }
//    }

    fun initLayout(){
        binding.apply {

            callBtn.setOnClickListener{
                callAction()
            }

            msgBtn.setOnClickListener{
                val message = Uri.parse("sms:010-1234-1234")
                val messageIntent = Intent(Intent.ACTION_SENDTO, message)
                messageIntent.putExtra("sms_body", "빨리 다음꺼 하자")
                startActivity(messageIntent)
            }

            webBtn.setOnClickListener{
                val webpage = Uri.parse("http://www.naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webpage)
                startActivity(webIntent)
            }

            mapBtn.setOnClickListener{
                val location = Uri.parse("geo:37.543684, 127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }

            cameraBtn.setOnClickListener {
                cameraAction()
            }
        }
    }

    private fun cameraAction(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(cameraPermissionGranted())
            startActivity(intent)
        else
            checkPermissions()
        // checkPermissions은 모든 권한이 허용되도록 설계됨
    }
}