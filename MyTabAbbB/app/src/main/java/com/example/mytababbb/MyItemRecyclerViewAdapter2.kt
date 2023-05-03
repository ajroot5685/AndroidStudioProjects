package com.example.mytababbb

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mytababbb.databinding.FragmentTeamBinding

class MyItemRecyclerViewAdapter2(
    private val values: List<String>
) : RecyclerView.Adapter<MyItemRecyclerViewAdapter2.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentTeamBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentTeamBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.teamcontent

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}