package com.example.gsbsuser.community

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gsbsuser.databinding.RowContentBinding

class ListAdapter(val items:MutableList<MyContent>):RecyclerView.Adapter<ListAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data: MyContent, position:Int)
    }
    var itemClickListener: OnItemClickListener?=null

    inner class ViewHolder(val binding: RowContentBinding):RecyclerView.ViewHolder(binding.root){
        init {
            binding.rowContent.setOnClickListener {
                itemClickListener?.onItemClick(items[bindingAdapterPosition], bindingAdapterPosition)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=RowContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.contentName.text=items[position].name
        holder.binding.contentDate.text=items[position].date
        holder.binding.contentWriter.text=items[position].writer
        holder.binding.contentLike.text=items[position].like.toString()
        holder.binding.contentComment.text=items[position].comment.toString()
    }
}