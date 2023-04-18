package com.example.assignment2

data class MyItem(var id:Int, var name:String, var description:String, var category:String, var count:Int=1):java.io.Serializable{
    // 다른 액티비티로의 객체전달을 위해 serializable 사용
}
