package com.newage.cozastore.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.newage.cozastore.R
import com.newage.cozastore.helper.DatabaseHelper

class AccountFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    //customer info
    lateinit var customerImage:ImageView
    lateinit var customerFirstName:EditText
    lateinit var customerLastName:EditText
    lateinit var customerEmail:EditText
    lateinit var customerContact:EditText
    lateinit var customerAddress:EditText

    //buttons
    lateinit var pickImage:ImageView
    lateinit var saveProfile:Button
    lateinit var changePassword:Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.let {
            //customer info
            customerImage = it.findViewById(R.id.customer_image)
            customerFirstName = it.findViewById(R.id.customer_first_name)
            customerLastName = it.findViewById(R.id.customer_last_name)
            customerEmail = it.findViewById(R.id.customer_email)
            customerContact = it.findViewById(R.id.customer_contact)
            customerAddress = it.findViewById(R.id.customer_address)

            //buttons
            pickImage = it.findViewById(R.id.edit_image)
            saveProfile = it.findViewById(R.id.save_customer_profile)
            changePassword = it.findViewById(R.id.change_customer_password)
        }



    }


    override fun onResume() {
        super.onResume()
        loadCustomer()
    }


    private fun loadCustomer(){
        val database = DatabaseHelper(requireContext())
        val customer = database.getCustomer()
        if(customer != null){

            customerFirstName.setText(customer.first_name)
            customerLastName.setText(customer.last_name)
            customerEmail.setText(customer.email_address)
            customerContact.setText(customer.phone_number)
            customerAddress.setText(customer.address)

        }
    }

}