package com.example.gsbsuser

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel2: ViewModel() {
    lateinit var item:MyContent
    fun setData(transitem:MyContent){
        item=transitem
    }
    fun getData():MyContent{
        return item
    }
}