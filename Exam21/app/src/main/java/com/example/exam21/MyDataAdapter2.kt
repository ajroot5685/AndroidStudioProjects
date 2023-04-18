package com.example.exam21

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam21.databinding.Row2Binding

class MyDataAdapter2(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyDataAdapter2.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: MyData, position: Int)
    }
    // 하나의 data 에 대해 서로다른 이벤트리스너 등록가능
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: Row2Binding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.row2Layout.setOnClickListener{
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = Row2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.num2TextView1.text=items[position].name
        holder.binding.num2TextView2.text=items[position].price.toString()
    }
}