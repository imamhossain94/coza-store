package com.newage.cozastore.api.models.product

class Item (var id:Int,
            var category_id:Int,
            var brand_id:Int,
            var user_id:Int,
            var product_name:String,
            var product_price:Float,
            var product_quantity:Int,
            var short_description:String,
            var long_description:String,
            var product_image:String,
            var product_image_mid:String,
            var product_image_low:String,
            var publication_status:Int,
            var created_at:String,
            var updated_at:String,
            var category_name:String,
            var brand_name:String){

    constructor():this(0,0,0,0,"",0f,0,"","", "", "", "",0,"","","","")

}


