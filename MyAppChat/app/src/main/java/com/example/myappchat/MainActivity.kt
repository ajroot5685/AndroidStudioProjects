package com.example.myappchat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.myappchat.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var url="https://api.openai.com/v1/completions"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.apply {
            sendBtn.setOnClickListener {
                responseTV.text="잠시 기다려주세요..."
                if(userQuestion.text.toString().length>0){
                    getResponse(userQuestion.text.toString())
                }else{
                    Toast.makeText(this@MainActivity,"질문을 입력해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getResponse(query: String) {
        binding.questionTV.text=query
        binding.userQuestion.setText("")

        val queue = Volley.newRequestQueue(this)
        val jsonObject=JSONObject()
        jsonObject.put("model","text-davinci-003")
        jsonObject.put("prompt",query)
        jsonObject.put("temperature",0)
        jsonObject.put("max_tokens",200)

        val postRequest = object: JsonObjectRequest(Method.POST, url, jsonObject,
            Response.Listener {
//                Log.i("check", it.toString())
                val msg = it.getJSONArray("choices").getJSONObject(0).getString("text")
                binding.responseTV.text=msg
            },
            Response.ErrorListener {
                Log.e("Error", it.message.toString())
            }){
            override fun getHeaders(): MutableMap<String, String> {
                val header = HashMap<String, String>()
                header["Content-Type"]="application/json"
//                header["Authorization"]="Bearer your-api-key"
                header["Authorization"]="Bearer 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"
                return header
            }
        }
        queue.add(postRequest)
    }
}