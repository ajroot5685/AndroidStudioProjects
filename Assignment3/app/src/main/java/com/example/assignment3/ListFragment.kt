package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.FragmentListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

class ListFragment : Fragment() {
    var binding: FragmentListBinding?=null
    lateinit var adapter: ListAdapter
    val model: MyViewModel by activityViewModels()
    val url="https://www.etnews.com/news/section.html?id1=04&id2=043"
    val scope= CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.swipe.setOnRefreshListener {
            binding!!.swipe.isRefreshing = true
            getapps()
        }

        binding!!.listView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding!!.listView.addItemDecoration(DividerItemDecoration(activity,LinearLayoutManager.VERTICAL))
        adapter=ListAdapter(ArrayList<MyContent>())
        binding!!.listView.adapter=adapter

        adapter.itemClickListener = object:ListAdapter.OnItemClickListener{
            override fun onItemClick(data: MyContent, position: Int) {
                model.setData(data)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val detailFragment = DetailFragment()
                fragment.replace(R.id.frameLayout, detailFragment)
                fragment.commit()
            }
        }
        getapps()

        // 검색 기능
        lateinit var tmpList:MutableList<MyContent>
        var searchList:ArrayList<MyContent> = ArrayList()

        binding!!.apply {
            searchBtn.setOnClickListener {
                var text = searchView.text.toString()
                tmpList=adapter.items
                searchList.clear()
                for(i in 0 until adapter.itemCount){
                    if(tmpList[i].title.contains(text)){
                        searchList.add(tmpList[i])
                    }
                }
                adapter.setSelect(searchList)
                adapter.notifyDataSetChanged()
                searchView.text.clear()
            }
        }
    }

    fun getapps(){
        scope.launch {
            adapter.items.clear()
//            adapter.notifyDataSetChanged()
//            kotlinx.coroutines.delay(500)
            val doc = Jsoup.connect(url).get()
            val headlines = doc.select("section.main_news_wrap>article>p>a")
            for(news in headlines){
                adapter.items.add(MyContent(news.text(), news.absUrl("href").toString(), adapter.itemCount+1))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding!!.swipe.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}