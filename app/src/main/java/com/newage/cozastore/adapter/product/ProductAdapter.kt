package com.newage.cozastore.adapter.product

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newage.cozastore.R
import com.newage.cozastore.api.models.product.Item
import com.newage.cozastore.application.CozaStore
import com.newage.cozastore.helper.replace
import kotlinx.android.synthetic.main.item_product_layout.view.*

class ProductAdapter(private val context: Context?, private var productList: ArrayList<Item>, private var wishList: ArrayList<Item>, val productListener: (Item) -> Unit, val wishListener: (Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_layout, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindItems(productList[position], wishList)

        holder.productButton.setOnClickListener { productListener(productList[position]) }
        holder.wishButton.setOnClickListener { wishListener(productList[position]) }

    }

    override fun getItemCount(): Int { return productList.size }

    fun filterProductList(filterProduct:ArrayList<Item>, filterWish:ArrayList<Item>){
        this.productList = filterProduct
        this.wishList = filterWish
        notifyDataSetChanged()
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view:FrameLayout = itemView.product_view

        val productButton:Button = itemView.product_button
        val wishButton:ImageView = itemView.wish_button

        @SuppressLint("SetTextI18n")
        fun bindItems(item: Item, wishList:ArrayList<Item>) {
            val productImage: ImageView = itemView.product_image
            val title = itemView.product_title
            val price = itemView.product_price

            title.text = item.product_name
            price.text = "৳ ${item.product_price}"

            if(item.product_image.isNotEmpty()){
                val imgUrl = item.product_image.replace("\\" to "", "[" to "", "]" to "", "\"" to "" )
                val outputItem = imgUrl.split(",").toTypedArray()
                Glide.with(itemView).load("http://cozastore.newagedevs.com/${outputItem[0]}").fitCenter().into(productImage)
            }

            view.layoutParams.width = CozaStore().getScreenWidth()/2-40
            view.layoutParams.height = (CozaStore().getScreenWidth()/2)+115

            if(wishList.any { h -> h.id == item.id }){
                wishButton.setImageResource(R.drawable.ic_heart_field)
            }else{
                wishButton.setImageResource(R.drawable.ic_heart_stroke)
            }

        }

    }
}


