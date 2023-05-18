package com.example.gsbsuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gsbsuser.databinding.RowCommentBinding

class ContentAdapter(val items:MutableList<MyComment>):RecyclerView.Adapter<ContentAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onItemClick(data:MyComment,position:Int)
    }
    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowCommentBinding): RecyclerView.ViewHolder(binding.root){
        init {

        }
    }

    override fun getItemViewType(position: Int): Int {
        return position;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= RowCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.commentWriter.text=items[position].writer
        holder.binding.commentContent.text=items[position].content
        holder.binding.commentDate.text=items[position].date
        holder.binding.commentLike.text=items[position].like.toString()
    }
}