package com.example.voc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.voc.databinding.RowBinding
import java.util.*
import kotlin.collections.ArrayList

class MyDataAdapter(val items:ArrayList<MyData>) : RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: MyData, position: Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding) : RecyclerView.ViewHolder(binding.root){
        init {
            binding.textView.setOnClickListener{
                itemClickListener?.onItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    fun moveItem(oldPos:Int, newPos:Int){
        // Collections.swap(items,oldPos,newPos)
        var item:MyData = items[oldPos]
        items.removeAt(oldPos)
        items.add(newPos, item)
        notifyItemMoved(oldPos,newPos)
    }

    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text=items[position].word
        holder.binding.textView2.text=items[position].meaning
        holder.binding.textView2.visibility=if(items[position].visible) View.VISIBLE else View.GONE
    }
}