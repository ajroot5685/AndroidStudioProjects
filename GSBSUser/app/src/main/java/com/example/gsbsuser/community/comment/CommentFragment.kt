package com.example.gsbsuser.community.comment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gsbsuser.R
import com.example.gsbsuser.community.CommentViewModel
import com.example.gsbsuser.community.board.Board
import com.example.gsbsuser.community.board.BoardAddFragment
import com.example.gsbsuser.community.board.BoardFragment
import com.example.gsbsuser.databinding.FragmentCommentBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
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
    private lateinit var commentdb: DatabaseReference
    private lateinit var likedb: DatabaseReference
    var boardId = ""
    var auth: FirebaseAuth?= null
    lateinit var currentUser: FirebaseUser
    lateinit var uid:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCommentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        currentUser=auth!!.currentUser!!
        uid = currentUser?.uid!!
        boardId = model.getBoardId()
        communitydb = Firebase.database.getReference("Community")
        likedb = Firebase.database.getReference("Like")
        var userRef = communitydb.child(boardId)
        userRef.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    initBtn()
                    initContent()
                    initComment()
                }
            }else{
                Toast.makeText(activity, "게시판 DB 조회 실패", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun initBtn() {
        binding!!.apply {
            boardAddbtn.setOnClickListener {
                val checkUser = FirebaseAuth.getInstance().currentUser
                if(checkUser?.isAnonymous==true){ // 익명 사용자일 경우
                    Toast.makeText(context, "익명 로그인의 경우 해당 기능을 이용할 수 없습니다.", Toast.LENGTH_LONG).show()
                }else{
                    val fragment = requireActivity().supportFragmentManager.beginTransaction()
                    fragment.addToBackStack(null)
                    val commentAddFragment = CommentAddFragment()
                    fragment.replace(R.id.contentLayout, commentAddFragment)
                    fragment.commit()
                }
            }
            boardLikebtn.setOnClickListener {
                checkLike()
            }
            boardDeletebtn.setOnClickListener {
                var userRef = communitydb.child(boardId).child("uid")
                userRef.get().addOnCompleteListener {
                        task->
                    if(task.isSuccessful){
                        val snapshot = task.result
                        if(snapshot.exists()){
                            val boarduid = task.result.value.toString()
                            if(boarduid == uid){
                                val dialogBuilder = AlertDialog.Builder(context)
                                dialogBuilder.setMessage("댓글을 삭제하시겠습니까?")
                                    .setPositiveButton("삭제하기", DialogInterface.OnClickListener{
                                            _, _ ->
                                        communitydb.child(boardId).removeValue()
                                            .addOnCompleteListener {
                                                    task->
                                                if(!task.isSuccessful){
                                                    Toast.makeText(activity, "board 삭제 실패", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        commentdb.removeValue()
                                            .addOnCompleteListener {
                                                    task->
                                                if(!task.isSuccessful){
                                                    Toast.makeText(activity, "comment 삭제 실패", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        val fragment = requireActivity().supportFragmentManager.beginTransaction()
                                        fragment.addToBackStack(null)
                                        val boardFragment = BoardFragment()
                                        fragment.replace(R.id.contentLayout, boardFragment)
                                        fragment.commit()
                                    })
                                    .setNegativeButton("취소", null)
                                    .create()
                                    .show()
                            }else{
                                Toast.makeText(activity, "게시글의 작성자만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(activity, "board 삭제 중 DB 접근 실패", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun checkLike() {
        var likeRef = likedb.child(boardId).child("like")
        likeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    // 해당 경로에 데이터가 없는 경우 생성
                    likeRef.setValue(0)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(activity, "like DB 접근 실패", Toast.LENGTH_SHORT).show()
            }
        })

        likeRef.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                val like:Int
                if(snapshot.exists()){
                    like = task.result.value.toString().toInt()
                }else{
                    like = 0
                }
                dbLike(like)
                if(like == 0){
                    likeRef.setValue(1)
                }else{
                    likeRef.setValue(0)
                }
            }else{
                Toast.makeText(activity, "like DB 접근 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun dbLike(like:Int){
        var userRef = communitydb.child(boardId).child("like")
        userRef.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    val likeCount:Int = if(like == 0){
                        task.result.value.toString().toInt()+1
                    }else{
                        task.result.value.toString().toInt()-1
                    }
                    userRef.setValue(likeCount)
                        .addOnCompleteListener {
                                task->
                            if(!task.isSuccessful){
                                Toast.makeText(activity, "board like DB 수정 실패2", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }else{
                Toast.makeText(activity, "board like DB 수정 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initContent() {
        communitydb.child(boardId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val data = snapshot.getValue(Board::class.java)
                    binding?.apply {
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

    private fun initComment() {
        commentdb = Firebase.database.getReference("Comments").child(boardId)
        commentdb.get().addOnCompleteListener {
                task->
            if(task.isSuccessful){
                val snapshot = task.result
                if(snapshot.exists()){
                    settingComment()
                }
            }else{
                Toast.makeText(activity, "댓글 DB 조회 실패", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun settingComment() {
        val query = commentdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<UserComment>()
            .setQuery(query, UserComment::class.java)
            .build()
        adapter= CommentAdapter(option)
        adapter.itemClickListener = object:CommentAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val commentId = adapter.getItem(position).commentid
                var count = adapter.getItem(position).like
                var likeRef = likedb.child(boardId+commentId).child("like")
                likeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (!snapshot.exists()) {
                            // 해당 경로에 데이터가 없는 경우 생성
                            likeRef.setValue(0)
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(activity, "like DB 접근 실패", Toast.LENGTH_SHORT).show()
                    }
                })
                var like=0
                likeRef.get().addOnCompleteListener {
                        task->
                    if(task.isSuccessful){
                        val snapshot = task.result
                        if(snapshot.exists()){
                            like = task.result.value.toString().toInt()
                        }
                    }else{
                        Toast.makeText(activity, "like DB 접근 실패", Toast.LENGTH_SHORT).show()
                    }
                    if(like == 0){
                        likeRef.setValue(1)
                        count++
                    }else{
                        likeRef.setValue(0)
                        count--
                    }
                    commentdb.child(commentId).child("like").setValue(count)
                        .addOnCompleteListener {
                                task->
                            if(!task.isSuccessful){
                                Toast.makeText(activity, "comment like DB 수정 실패", Toast.LENGTH_SHORT).show()
                            }
                        }
                }
            }
        }
        adapter.deleteClickListener = object :CommentAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                val commentuid = adapter.getItem(position).uid
                val commentid=adapter.getItem(position).commentid
                if(uid==commentuid){
                    val dialogBuilder = AlertDialog.Builder(context)
                    dialogBuilder.setMessage("댓글을 삭제하시겠습니까?")
                        .setPositiveButton("삭제하기", DialogInterface.OnClickListener{
                            _, _ ->
                            commentdb.child(commentid).removeValue()
                                .addOnCompleteListener {
                                        task->
                                    if(!task.isSuccessful){
                                        Toast.makeText(activity, "comment 삭제 실패", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            var userRef = communitydb.child(boardId).child("comment")
                            userRef.get().addOnCompleteListener {
                                    task->
                                if(task.isSuccessful){
                                    val snapshot = task.result
                                    if(snapshot.exists()){
                                        val commentCount = task.result.value.toString().toInt()-1
                                        userRef.setValue(commentCount)
                                            .addOnCompleteListener {
                                                    task->
                                                if(!task.isSuccessful){
                                                    Toast.makeText(activity, "board comment DB 수정 실패2", Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                    }
                                }else{
                                    Toast.makeText(activity, "board comment DB 수정 실패", Toast.LENGTH_SHORT).show()
                                }
                            }
                        })
                        .setNegativeButton("취소", null)
                        .create()
                        .show()
                }
                else{
                    Toast.makeText(activity, "댓글 작성자만 삭제할 수 있습니다.", Toast.LENGTH_SHORT).show()
                }
            }

        }
        binding!!.commentView.layoutManager=LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding!!.commentView.adapter=adapter
        adapter.startListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}