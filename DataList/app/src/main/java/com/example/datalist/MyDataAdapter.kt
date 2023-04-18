package com.example.datalist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.datalist.databinding.RowBinding

class MyDataAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text=items[position].textString
        holder.binding.textView.textSize=items[position].textPt.toFloat()
    }
}