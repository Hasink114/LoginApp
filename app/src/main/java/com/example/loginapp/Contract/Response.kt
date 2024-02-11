package com.example.loginapp.Contract

data class Response(

    var status: Boolean = false,
    var responseCode: Int = -1,
    var message: String = "",
    var id: String = "",
    var username: String = ""

)
