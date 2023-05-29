package com.example.gsbsuser.community

import androidx.lifecycle.ViewModel
import com.example.gsbsuser.community.board.Board

class CommentViewModel: ViewModel() {
    lateinit var boardid: String
    fun setBoardId(id : String){
        boardid=id
    }
    fun getBoardId(): String {
        return boardid
    }
}