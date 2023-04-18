package com.example.exam21

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exam21.databinding.RowBinding

class MyDataAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: MyData, position: Int)
    }
    // 하나의 data 에 대해 서로다른 이벤트리스너 등록가능
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.textView.setOnClickListener{
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text=items[position].name
    }
}