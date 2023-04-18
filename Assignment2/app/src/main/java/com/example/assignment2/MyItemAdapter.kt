package com.example.assignment2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.databinding.RowBinding

class MyItemAdapter(val tempitems:ArrayList<MyItem>):RecyclerView.Adapter<MyItemAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data:MyItem,position:Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.descriptionLayout.setOnClickListener{
                itemClickListener?.onItemClick(tempitems[adapterPosition],adapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tempitems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.description1.text=tempitems[position].name
        holder.binding.description2.text=tempitems[position].description
    }
}