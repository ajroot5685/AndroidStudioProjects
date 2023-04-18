package com.example.voc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.telephony.NetworkScan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.voc.databinding.ActivityMainBinding
import java.io.FileNotFoundException
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter
    lateinit var tts:TextToSpeech
    var isTTsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
        initTTS()
    }

    private fun initTTS(){
        tts = TextToSpeech(this){
            isTTsReady = true
            tts.language = Locale.US
        }
    }

    fun readScanFile(scan:Scanner){
        while(scan.hasNextLine()){
            val word=scan.nextLine()
            val meaning=scan.nextLine()
            data.add(MyData(word, meaning))
        }
    }

    fun initData(){
        // voc.txt 파일을 먼저 읽어 recyclerView에서 위쪽에 위치함
        try{
            val scan2 = Scanner(openFileInput("voc.txt"))
            readScanFile(scan2)
        }catch (e: FileNotFoundException){

        }

        val scan = Scanner(resources.openRawResource(R.raw.words))
        readScanFile(scan)
    }

    fun initRecyclerView(){
        binding.recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = MyDataAdapter(data)
        adapter.itemClickListener=object :MyDataAdapter.OnItemClickListener{
            override fun onItemClick(data: MyData, position: Int) {
//                Toast.makeText(this@MainActivity,data.meaning,Toast.LENGTH_SHORT).show();
                if(isTTsReady)
                    tts.speak(data.word, TextToSpeech.QUEUE_ADD, null, null)

                data.visible = !data.visible
                adapter.notifyItemChanged(position)
            }

        }
        binding.recyclerView.adapter = adapter
        val simpleCallback = object:ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
    }

    override fun onStop() {
        super.onStop()
        tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        tts.shutdown()
    }
}