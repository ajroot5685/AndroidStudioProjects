package com.example.jganmidtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jganmidtest.databinding.RowBinding

class MyDataAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: MyData, position: Int)
    }
    // 하나의 data 에 대해 서로다른 이벤트리스너 등록가능
    var itemClickListener:OnItemClickListener?=null
    var itemClickListener2:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.descriptionLayout.setOnClickListener{
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
            binding.callImage.setOnClickListener {
                itemClickListener2?.onItemClick(items[adapterPosition], adapterPosition)
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
        holder.binding.description1.text=items[position].name
        holder.binding.description2.text=items[position].company
        holder.binding.description3.text=items[position].call
        holder.binding.countBtn.text=items[position].count.toString()
    }
}