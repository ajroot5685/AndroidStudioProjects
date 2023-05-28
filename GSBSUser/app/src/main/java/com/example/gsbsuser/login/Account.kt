package com.example.gsbsuser.login

data class Account(
    var pId:String,
    var pPw: String,
    var pName: String,
    var pNickname: String,
    var uId: String
){
    constructor(): this("","","","","")
}