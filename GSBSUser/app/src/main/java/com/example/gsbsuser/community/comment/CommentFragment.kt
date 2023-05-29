package com.example.gsbsuser.community.comment

import android.os.Bundle
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
import com.example.gsbsuser.community.board.Board
import com.example.gsbsuser.community.board.BoardAdapter
import com.example.gsbsuser.databinding.FragmentBoardBinding
import com.example.gsbsuser.databinding.FragmentCommentBinding
import com.example.gsbsuser.login.Account
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CommentFragment : Fragment() {
    var binding: FragmentCommentBinding?=null
    lateinit var adapter: CommentAdapter
    val model: CommentViewModel by activityViewModels()
    private lateinit var communitydb: DatabaseReference
    var boardId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        communitydb = Firebase.database.getReference("Community")
        boardId = model.getBoardId()
        var userRef = communitydb.child(boardId)
        userRef.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    settingFromDB()
                    settingComment()
                }
            }else{
                Toast.makeText(activity, "게시판 DB 조회 실패", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun settingFromDB() {
        communitydb.child(boardId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val data = snapshot.getValue(Board::class.java)
                    binding!!.apply {
                        boardName.text=data!!.name
                        boardContent.text=data!!.content
                        boardDate.text=data!!.date
                        boardWriter.text=data!!.writer
                        boardLike.text=data!!.like.toString()
                        boardComment.text=data!!.comment.toString()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "DB 조회 중 에러 발생", Toast.LENGTH_LONG).show()
            }
        })

    }

    private fun settingComment() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}