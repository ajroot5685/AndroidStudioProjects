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
import com.example.gsbsuser.databinding.FragmentBoardBinding

class BoardFragment : Fragment() {
    var binding: FragmentBoardBinding?=null
    lateinit var adapter: ContentAdapter
    val model2: MyViewModel2 by activityViewModels()
    lateinit var item:MyContent

    var dataList: ArrayList<MyComment> = arrayListOf(
        MyComment("writer1","content1","date1",1,0),
        MyComment("writer2","content1","date1",1,0),
        MyComment("writer3","content1","date1",1,0),
        MyComment("writer4","content1","date1",1,0),
        MyComment("writer5","content1","date1",1,0),
        MyComment("writer6","content1","date1",1,0),
        MyComment("writer7","content1","date1",1,0)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        item=model2.getData()
        binding!!.apply {
            boardName.text=item.name
            boardContent.text=item.content
            boardDate.text=item.date
            boardWriter.text=item.writer
            boardLike.text=item.like.toString()
            boardComment.text=item.comment.toString()
        }

        adapter= ContentAdapter(dataList)
        binding!!.commentView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        binding!!.commentView.adapter=adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}