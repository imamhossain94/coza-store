package com.newage.cozastore.api.models

data class User(var id:Int,
                var name:String,
                var email:String,
                var email_verified_at:String,
                var password:String,
                var role:String,
                var remember_token:String,
                var created_at:String,
                var updated_at:String){

    constructor():this(0,"","","","",
    "","","","")


}