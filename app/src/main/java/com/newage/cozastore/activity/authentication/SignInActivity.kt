package com.newage.cozastore.activity.authentication

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.reflect.TypeToken
import com.newage.cozastore.R
import com.newage.cozastore.api.models.Customer
import com.newage.cozastore.api.models.LogIn
import com.newage.cozastore.api.services.ApiBuilder
import com.newage.cozastore.api.services.ApiService
import com.newage.cozastore.helper.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    private var emailAddress = ""
    private var passwordVar = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mContext = this

    }

    private fun inputValidator():Boolean{

        var isValid = true

        if(emailAddress.isEmpty()){
            email_address.error = "Field Required";isValid = false
        }

        if(passwordVar.isEmpty()){
            password.error = "Field Required";isValid = false
        }

        if(!emailAddress.contains("@") && !emailAddress.contains(".") && !emailAddress.contains("com")){
            email_address.error = "Invalid Email";isValid = false
        }

        return isValid

    }

    fun signUpClick(view: View){
        this.switchIntent(SignUpActivity())
        finish()
    }

    fun signInCustomer(view: View) {

        emailAddress = email_address.text.toString()
        passwordVar = password.text.toString()

        if(inputValidator()){

            val login = LogIn()

            this.showLoadingDialog()

            login.email_address = emailAddress
            login.password = passwordVar

            val apiService = ApiBuilder.buildService(ApiService::class.java)
            val requestCall = apiService.logInCustomer(login)

            requestCall.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    applicationContext.toastyError("Sign in failed")
                    this@SignInActivity.hideLoadingDialog()
                }

                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    this@SignInActivity.hideLoadingDialog()
                    if (response.isSuccessful) {
                        val responseString = response.body()?.string()

                        if(responseString!!.contains("Invalid")){
                            applicationContext.toastyError(responseString.replace('"','\u0000'))
                        }else{
                            val output = outputRes(responseString, object : TypeToken<Customer>() {}.type) as Customer

                            val database = DatabaseHelper(this@SignInActivity)
                            database.deleteCustomer()
                            database.saveCustomer(output)

                            finish()
                        }

                    } else {
                        applicationContext.toastyError("Sign in failed")
                    }
                }
            })
        }
    }
}



