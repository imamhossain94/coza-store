package com.newage.cozastore.api.models

data class Slider(var id:Int,
                  var slider_subtitle:String,
                  var slider_title:String,
                  var slider_image:String,
                  var slider_image_mid:String,
                  var slider_image_low:String,
                  var publication_status:Int,
                  var created_at:String,
                  var updated_at:String ){

    constructor():this(0,"","","", "","",0,"","")

}
