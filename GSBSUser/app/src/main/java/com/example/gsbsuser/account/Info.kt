package com.example.gsbsuser.account

data class Info(
    var email:String,
    var call:String,
    var birth:String,
    var major:String,
    var introduce:String
    ){
    constructor(): this("","","2000-01-01","","")
}
