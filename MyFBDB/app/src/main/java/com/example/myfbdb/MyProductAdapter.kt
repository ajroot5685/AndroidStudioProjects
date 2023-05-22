package com.example.myfbdb

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myfbdb.databinding.RowBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyProductAdapter(options: FirebaseRecyclerOptions<Product>) : FirebaseRecyclerAdapter<Product, MyProductAdapter.ViewHolder>(options) {

    interface OnItemClickListener{
        fun OnItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null

    inner class ViewHolder(val binding: RowBinding): RecyclerView.ViewHolder(binding.root){
        init {
            binding.root.setOnClickListener{
                itemClickListener!!.OnItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product) {
        holder.binding.apply {
            productid.text=model.pId.toString()
            productname.text=model.pName
            productquantity.text=model.pQuantity.toString()
        }
    }
}