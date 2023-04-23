package com.example.applist

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL, false
        )

        adapter = MyAdapter(ArrayList<MyData>())
        val intent=Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val applist=
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            packageManager.queryIntentActivities(
                intent, PackageManager.ResolveInfoFlags.of(0))
        } else {
                @Suppress("DEPRECATION")    // deprecation 된 경고문 무시
                packageManager.queryIntentActivities(intent,0)
            }

        if(applist.size>0){
            for(appinfo in applist){
                var applabel = appinfo.loadLabel(packageManager).toString()
                var appclass = appinfo.activityInfo.name
                var apppackname = appinfo.activityInfo.packageName
                var appicon = appinfo.loadIcon(packageManager)
                adapter.items.add(MyData(applabel,appclass, apppackname, appicon))
            }
        }

        adapter.itemClickListener = object : MyAdapter.OnItemClickListener {
            override fun onItemClick(data: MyData, position: Int) {
                val intent = packageManager.getLaunchIntentForPackage(data.apppackname)
                startActivity(intent)
            }
        }

        binding.recyclerView.adapter=adapter
    }
}