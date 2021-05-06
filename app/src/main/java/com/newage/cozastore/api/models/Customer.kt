package com.newage.cozastore.api.models

class Customer(var id:Int,
               var first_name:String,
               var last_name:String,
               var email_address:String,
               var password:String,
               var confirm_password:String,
               var phone_number:String,
               var address:String) {
    constructor():this(0,"","","","","",
    "","")

    constructor( first_name:String,
                 last_name:String,
                 email_address:String,
                 password:String,
                 confirm_password:String,
                 phone_number:String,
                 address:String):this(){

        this.first_name = first_name
        this.last_name = last_name
        this.email_address = email_address
        this.password = password
        this.confirm_password = confirm_password
        this.phone_number = phone_number
        this.address = address

    }

    constructor( first_name:String,
                 last_name:String,
                 email_address:String,
                 password:String,
                 phone_number:String,
                 address:String):this(){

        this.first_name = first_name
        this.last_name = last_name
        this.email_address = email_address
        this.password = password
        this.phone_number = phone_number
        this.address = address

    }

}