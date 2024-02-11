package com.example.loginapp

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.loginapp.Contract.Request
import com.example.loginapp.Contract.Response
import com.example.loginapp.Utils.showToast
import com.example.loginapp.databinding.ActivityMainBinding
import com.example.loginapp.network.IRequest
import com.example.loginapp.network.NetworkClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.create
import java.io.IOException

class MainActivity : AppCompatActivity(), Callback<Response> {

    private lateinit var binding: ActivityMainBinding
    private  lateinit var progressDialog: ProgressDialog
    private val retrofitClient = NetworkClient.getNetworkClient()
    private val requestContract = retrofitClient.create(IRequest::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Please wait.............")
        progressDialog.setCancelable(true)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            loginBtn.setOnClickListener {
                if (emailBox.text.toString().isNullOrEmpty()){
                    showToast("Please enter your email")
                }
                else if (passwordBox.text.toString().isNullOrEmpty()){
                    showToast("Please enter your password")
                }
                else{
                    progressDialog.show()
                    val request = Request(
                        action = "Login",
                        email = emailBox.text.toString(),
                        password = passwordBox.text.toString()
                    )
                    val callResponse = requestContract.makeApiCall(request)
                    callResponse.enqueue(this@MainActivity)
                }
            }
        }
    }

    override fun onResponse(call: Call<Response>, response: retrofit2.Response<Response>) {
        if (progressDialog.isShowing){
            progressDialog.dismiss()
        }

        if(response.body()!=null){
            val serveResponse = response.body()
            if(serveResponse!!.status){
                showToast(serveResponse.message)
            }
        }
        else{
            showToast("Server is not responding!")
        }
    }

    override fun onFailure(call: Call<Response>, t: Throwable) {
        if (progressDialog.isShowing){
            progressDialog.dismiss()
        }

        if (t is IOException){
            showToast("Network Error")
        }
        else{
            showToast("Unexpected Error.")
        }
    }
}