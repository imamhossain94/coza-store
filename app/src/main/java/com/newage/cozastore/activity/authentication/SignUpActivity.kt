package com.newage.cozastore.activity.authentication

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import com.google.gson.reflect.TypeToken
import com.newage.cozastore.R
import com.newage.cozastore.api.models.Customer
import com.newage.cozastore.api.services.ApiBuilder
import com.newage.cozastore.api.services.ApiService
import com.newage.cozastore.helper.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var mContext: Context

    private var firstName = ""
    private var lastName = ""
    private var emailAddress = ""
    private var passwordVar = ""
    private var confirmPassword = ""
    private var phoneNumber = ""
    private var addressVar = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        mContext = this


        val semesterSpinner = ArrayAdapter.createFromResource(this,R.array.country_code,R.layout.spinner_item_strok_black)
        semesterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        country_code_spinner.adapter = semesterSpinner
        country_code_spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {}
        }

    }

    private fun inputValidator():Boolean{

        var isValid = true

        if(firstName.isEmpty()){
            first_name.error = "Field Required";isValid = false
        }

        if(lastName.isEmpty()){
            last_name.error = "Field Required";isValid = false
        }

        if(emailAddress.isEmpty()){
            email_address.error = "Field Required";isValid = false
        }

        if(passwordVar.isEmpty()){
            password.error = "Field Required";isValid = false
        }

        if(passwordVar.length < 8){
            password.error = "Min 8 character";isValid = false
        }

        if(confirmPassword.isEmpty()){
            confirm_password.error = "Field Required";isValid = false
        }

        if(confirmPassword.length < 8){
            confirm_password.error = "Min 8 character";isValid = false
        }

        if(phoneNumber.isEmpty()){
            phone_number.error = "Field Required";isValid = false
        }

        if(phoneNumber.length != 11){
            phone_number.error = "Invalid Phone [Min 11 character]";isValid = false
        }

        if(addressVar.isEmpty()){
            address.error = "Field Required";isValid = false
        }

        if(addressVar.length < 12){
            address.error = "Min 11 character";isValid = false
        }

        if(!emailAddress.contains("@") && !emailAddress.contains(".") && !emailAddress.contains("com")){
            email_address.error = "Invalid Email";isValid = false
        }

        if(passwordVar != confirmPassword){
            password.error = "Not Matched"
            confirm_password.error = "Not Matched";isValid = false
        }

        return isValid
    }

    fun signInClick(view: View){
        this.switchIntent(SignInActivity())
        finish()
    }

    fun signUpCustomer(view: View) {

        firstName = first_name.text.toString()
        lastName = last_name.text.toString()
        emailAddress = email_address.text.toString()
        passwordVar = password.text.toString()
        confirmPassword = confirm_password.text.toString()
        phoneNumber = phone_number.text.toString()
        addressVar = address.text.toString()

        if(inputValidator()){
            val customer = Customer()

            this.showLoadingDialog()

            customer.first_name = firstName
            customer.last_name = lastName
            customer.email_address = emailAddress
            customer.password = passwordVar
            customer.confirm_password = confirmPassword
            customer.phone_number = "+88$phoneNumber"
            customer.address = addressVar

            val apiService = ApiBuilder.buildService(ApiService::class.java)
            val requestCall = apiService.signUpCustomer(customer)

            requestCall.enqueue(object: Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    this@SignUpActivity.hideLoadingDialog()
                    mContext.toastyError("Sign up failed")
                }
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    this@SignUpActivity.hideLoadingDialog()

                    if (response.isSuccessful) {
                        val responseString = response.body()?.string()

                        if(responseString!!.contains("id")){
                            val output = outputRes(responseString, object : TypeToken<Customer>() {}.type) as Customer

                            val database = DatabaseHelper(this@SignUpActivity)
                            database.deleteCustomer()
                            database.saveCustomer(output)

                            finish()
                        }else{

                            if(responseString.contains("The password must be at least 8 characters.")){
                                password.error  = "The password must be at least 8 characters."
                                confirm_password.error = "The password must be at least 8 characters."
                            }

                            if(responseString.contains("The address must be at least 12 characters.")){
                                address.error  = "The address must be at least 12 characters."
                            }

                            if(responseString.contains("The email address has already been taken.")){
                                address.error  = "The email address has already been taken."
                            }
                        }

                    } else {
                        mContext.toastyError("Sign up failed")
                    }
                }
            })
        }
    }


}