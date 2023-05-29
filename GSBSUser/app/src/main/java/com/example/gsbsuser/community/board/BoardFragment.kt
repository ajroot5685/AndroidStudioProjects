package com.example.gsbsuser.community.board

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gsbsuser.R
import com.example.gsbsuser.community.CommentViewModel
import com.example.gsbsuser.community.comment.CommentFragment
import com.example.gsbsuser.databinding.FragmentBoardBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.log

class BoardFragment : Fragment() {
    var binding: FragmentBoardBinding?=null
    lateinit var adapter: BoardAdapter
    val model: CommentViewModel by activityViewModels()
    private lateinit var communitydb: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initContent()
        initBtn()
    }

    private fun initBtn() {
        binding!!.apply {
            boardAdd.setOnClickListener {
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val boardAddFragment = BoardAddFragment()
                fragment.replace(R.id.contentLayout, boardAddFragment)
                fragment.commit()
            }
            searchButton.setOnClickListener {
                findFromDB()
            }
        }
    }

    private fun initContent() {
        communitydb=Firebase.database.getReference("Community")
        communitydb.get().addOnCompleteListener {
            task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    settingFromDB()
                }
            }else{
                Toast.makeText(activity, "게시판 DB 조회 실패", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun settingFromDB() {
        val query = communitydb.limitToLast(100)
        val option = FirebaseRecyclerOptions.Builder<Board>()
            .setQuery(query, Board::class.java)
            .build()
        adapter= BoardAdapter(option)
        adapter.itemClickListener = object:BoardAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val boardId = adapter.getItem(position).boardid
                model.setBoardId(boardId)
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val commentFragment = CommentFragment()
                fragment.replace(R.id.contentLayout, commentFragment)
                fragment.commit()
            }

        }
        binding!!.listView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.listView.adapter=adapter
        adapter.startListening()
    }

    private fun findFromDB() {
        val word = binding!!.searchEditText.text.toString()
        if(word==""){ // 모든 쿼리 찾기
            settingFromDB()
        }else {
            var query = communitydb.orderByChild("name").equalTo(word)
            val option = FirebaseRecyclerOptions.Builder<Board>()
                .setQuery(query, Board::class.java)
                .build()
            adapter = BoardAdapter(option)
            adapter.itemClickListener = object : BoardAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    binding.apply {
                        val fragment = requireActivity().supportFragmentManager.beginTransaction()
                        fragment.addToBackStack(null)
                        val commentFragment = CommentFragment()
                        fragment.replace(R.id.contentLayout, commentFragment)
                        fragment.commit()
                    }
                }
            }

            if (adapter != null) {
                binding!!.listView.adapter = adapter
                adapter.startListening()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}