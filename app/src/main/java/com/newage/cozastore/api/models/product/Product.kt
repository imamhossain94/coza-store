package com.newage.cozastore.api.models.product

class Product(var current_page:Int,
              var data:ArrayList<Item>,
              var first_page_url:String,
              var from:Int,
              var last_page:Int,
              var last_page_url:String,
              var next_page_url:String,
              var path:String,
              var per_page:Int,
              var prev_page_url:String,
              var to:Int,
              var total:Int) {

    constructor():this(0,ArrayList(),"",0,0,"","",
    "",0,"",0,0)

}

