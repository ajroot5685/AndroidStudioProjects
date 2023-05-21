package com.example.assignment3

import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    lateinit var item:MyContent
    fun setData(transitem:MyContent){
        item=transitem
    }
    fun getData():MyContent{
        return item
    }
}