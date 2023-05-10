package com.example.mygetnews

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygetnews.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url="https://news.daum.net"
    val rssurl="https://fs.jtbc.co.kr/RSS/culture.xml"
    val musicurl="https://www.melon.com/chart/index.htm"
    val scope= CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun getnews(){
        scope.launch {
            adapter.items.clear()
            // get() 의 default는 html parser
            val doc = Jsoup.connect(url).get()
//            Log.i("check", doc.toString())
            val headlines = doc.select("ul.list_newsissue>li>div.item_issue>div>strong.tit_g>a")
            for(news in headlines){
                adapter.items.add(MyData(news.text(), news.absUrl("href")))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    fun getrssnews(){
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(rssurl).parser(Parser.xmlParser()).get()
//            Log.i("check", doc.toString())
            val headlines = doc.select("item")
            for(news in headlines){
                adapter.items.add(MyData(news.select("title").text(), news.select("link").text()))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    fun getmusics(){
        scope.launch {
            adapter.items.clear()
            // get() 의 default는 html parser
            val doc = Jsoup.connect(musicurl).get()
//            Log.i("check", doc.toString())
            val headlines = doc.select("form#frm>div>table>tbody>tr.lst50")
            for(news in headlines){
//                adapter.items.add(MyData(news.text(),""))
                adapter.items.add(MyData(news.select("div.wrap_song_info>div.ellipsis.rank01").text(),""))
                adapter.items.add(MyData(news.select("td>div.wrap>div.wrap_song_info>div.ellipsis.rank02>a").text(), ""))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing = false
            }
        }
    }

    private fun initLayout(){
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing = true
//            getnews()
//            getrssnews()
            getmusics()
        }
        binding.recyclerView.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        adapter = MyAdapter(ArrayList<MyData>())
        adapter.itemClickListener = object:MyAdapter.OnItemClickListener{
            override fun OnItemClick(position: Int){
                val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].url))
                startActivity(intent)
            }
        }
        binding.recyclerView.adapter=adapter
//        getnews()
//        getrssnews()
        getmusics()
    }
}