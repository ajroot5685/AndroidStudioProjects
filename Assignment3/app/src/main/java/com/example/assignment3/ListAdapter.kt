package com.example.assignment3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment3.databinding.RowBinding

class ListAdapter(var items:MutableList<MyContent>):RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data:MyContent,position:Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.title.setOnClickListener {
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
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
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.title.text=items[position].title
        holder.binding.rank.text=items[position].rank.toString()
    }

    fun setSelect(select:ArrayList<MyContent>){
        items=select
        notifyDataSetChanged()
    }
}