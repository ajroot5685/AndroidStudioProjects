package com.example.gsbsuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsbsuser.databinding.FragmentListBinding

class ListFragment : Fragment() {
    var binding: FragmentListBinding?=null
    lateinit var adapter: ListAdapter
    val model2: MyViewModel2 by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var list: ArrayList<MyContent> = requireActivity().intent!!.extras!!.get("DataList") as ArrayList<MyContent>
        Log.e("ListFragment", "Data List: ${list}")

        adapter=ListAdapter(list)
        binding!!.listView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding!!.listView.adapter=adapter

        adapter.itemClickListener = object:ListAdapter.OnItemClickListener{
            override fun onItemClick(data: MyContent, position: Int) {
                model2.setData(data)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val boardFragment = BoardFragment()
                fragment.replace(R.id.contentLayout, boardFragment)
                fragment.commit()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}