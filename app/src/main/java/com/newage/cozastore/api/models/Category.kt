package com.newage.cozastore.api.models

data class Category(var id:Int,
                    var category_name:String,
                    var category_description:String,
                    var category_image:String,
                    var category_image_mid:String,
                    var category_image_low:String,
                    var publication_status:String,
                    var updated_at:String,
                    var created_at:String){
    constructor():this(0,"","","","","","","","")
}