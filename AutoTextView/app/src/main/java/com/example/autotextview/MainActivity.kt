package com.example.autotextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.autotextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    val countries= mutableListOf<String>(
        "Afghanistan", "Albania", "Algeria","American Samoa","Andorra","Bahrain",
        "Bangladesh","Barbados","Belarus","Belgium")
    lateinit var countries2: Array<String>

    lateinit var adapter1:ArrayAdapter<String>
    lateinit var adapter2:ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout(){
        // string.xml의 string-array 불러오기
        countries2=resources.getStringArray(R.array.countries_array)
        adapter1 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries)
        adapter2 = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, countries2)

        binding.autoCompleteTextView.setAdapter(adapter1)
        binding.autoCompleteTextView.setOnItemClickListener{
            adapterView,view,i, id->
            val item=adapterView.getItemIdAtPosition(i).toString()
            Toast.makeText(this,"선택된 나라 : $item", Toast.LENGTH_SHORT).show()
        }

        binding.multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        binding.multiAutoCompleteTextView.setAdapter(adapter2)

        // textWatcher 사용 2번째 방법
        binding.editText.addTextChangedListener {
            val str=it.toString()
            binding.button.isEnabled=str.isNotEmpty()
        }

        // textWatcher 사용 3번째 방법
        binding.editText.addTextChangedListener(
            afterTextChanged = {
                val str=it.toString()
                binding.button.isEnabled=str.isNotEmpty()
            }
        )

        // textWatcher 사용 1번째 방법
//        binding.editText.addTextChangedListener(object:TextWatcher{
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                //TODO("Not yet implemented")
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//                val str=p0.toString()
//                binding.button.isEnabled=str.isNotEmpty()
//            }
//
//        })

        binding.button.setOnClickListener{
            adapter1.add(binding.editText.text.toString())  // 배열이 아닌 adapter에 추가
            adapter1.notifyDataSetChanged()         // 데이터 변경되었음을 알림
            binding.editText.text.clear()       // 입력한 텍스트 지우기
        }
    }
}