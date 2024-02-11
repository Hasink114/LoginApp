package com.example.loginapp.network

import com.example.loginapp.Contract.Request
import com.example.loginapp.Contract.Response
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IRequest {
    @POST("service.php")
    fun makeApiCall(@Body request: Request): Call<Response>
}