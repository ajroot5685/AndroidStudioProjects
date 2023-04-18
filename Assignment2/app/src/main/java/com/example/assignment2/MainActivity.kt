package com.example.assignment2

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment2.databinding.ActivityMainBinding
import java.util.Scanner

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val item:ArrayList<MyItem> = ArrayList()
    lateinit var adapter:MyItemAdapter
    var itemidcount:Int = 7

    // 퍼미션 설정
    val permissions = arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    // 이미지 가져오기 위한 launcher
    lateinit var getResultText: ActivityResultLauncher<Intent>

    // 이미지를 갤러리에서 가져올 수는 있었지만 앱에 그 이미지를 저장시키는 것은 아직 배우지 않은 부분이기에 건너뛰었다.

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
            (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    -> {
                        Toast.makeText(this,"모든 권한 승인됨",Toast.LENGTH_SHORT).show()
                    }
            ActivityCompat.shouldShowRequestPermissionRationale(this,android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    ->{
                        permissionCheckAlertDlg()
                    }
            else ->{
                multiplePermissionLauncher.launch(permissions)
            }
        }
    }

    fun permissionCheckAlertDlg() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 갤러리 접근에 대한 권한이 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                _,_-> multiplePermissionLauncher.launch(permissions)
            }.setNegativeButton("Cancel"){
                dlg,_-> dlg.dismiss()
            }
        val dlg = builder.create()
        dlg.show()
    }

    fun allPermissionsGranted() = permissions.all{
        ActivityCompat.checkSelfPermission(this,it)==PackageManager.PERMISSION_GRANTED
    }
    // 권한 설정 끝

    // 이미지 가져오기
    fun galleryaction(){
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type=MediaStore.Images.Media.CONTENT_TYPE
        if(allPermissionsGranted())
            getResultText.launch(galleryIntent)
        else
            checkPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initItem()
        initRecyclerView()
        initInputButton()
        init()
    }

    fun initItem(){
        val scan=Scanner(resources.openRawResource(R.raw.items))
        while(scan.hasNextLine()){
            val id= getItemId()
            val name=scan.nextLine()
            val description=scan.nextLine()
            val category = "Outer".toString()
            item.add(MyItem(id,name,description, category))
        }
    }

    fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter= MyItemAdapter(item)
        adapter.itemClickListener=object :MyItemAdapter.OnItemClickListener{
            override fun onItemClick(data: MyItem, position: Int) {
                val intent = Intent(this@MainActivity, ItemDetail::class.java)  // 변수명을 무조건 'intent' 라고 해야 하는듯
                intent.putExtra("key",data)
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter=adapter
    }

    fun initInputButton(){
        binding.inputButton.setOnClickListener{
            val inputId = getItemId()
            val inputName=binding.itemNameLayout1.editText?.text.toString()
            val inputDescription=binding.itemNameLayout2.editText?.text.toString()
            val inputCategory = binding.spinnerCategory.selectedItem.toString()

            // 전에 작성했던 데이터들 지우기
            binding.itemName1.setText(null)
            binding.itemName2.setText(null)
            binding.itemName3.setText(null)
            binding.spinnerCategory.setSelection(0)
            binding.registerImage.setImageURI(null)

            item.add(MyItem(inputId, inputName, inputDescription, inputCategory))
            Toast.makeText(this,"상품이 등록되었습니다!", Toast.LENGTH_SHORT).show()
        }
        binding.getImageButton.setOnClickListener{
            galleryaction()
        }
    }

    fun init(){
        binding.textView1.setOnClickListener{
            binding.inputLayout.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE
        }
        binding.textView2.setOnClickListener{
            binding.recyclerView.visibility = View.GONE
            binding.inputLayout.visibility = View.VISIBLE
        }

        // 이미지 가져오기 위한 코드
        getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result ->
            if(result.resultCode== RESULT_OK){
                val intent = checkNotNull(result.data)
                val imageUri = intent.data
                binding.registerImage.setImageURI(imageUri)
            }
        }
    }

    fun getItemId():Int{
        return ++itemidcount;
    }
}