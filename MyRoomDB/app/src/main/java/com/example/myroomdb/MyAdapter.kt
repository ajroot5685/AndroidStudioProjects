package com.example.myroomdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myroomdb.databinding.RowBinding

class MyAdapter(var items:ArrayList<Product>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.layout.setOnClickListener{
                itemClickListener?.onItemClick(adapterPosition)
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
        holder.binding.pId.text = items[position].pId.toString()
        holder.binding.pName.text = items[position].pName
        holder.binding.pQuantity.text = items[position].pQuantity.toString()
    }
}