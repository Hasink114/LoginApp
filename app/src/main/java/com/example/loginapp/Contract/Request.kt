package com.example.loginapp.Contract

import com.google.gson.annotations.SerializedName

data class Request(

    @SerializedName("action") var action: String = "",
    @SerializedName("email") var email: String = "",
    @SerializedName("password") var password: String = "",

)
