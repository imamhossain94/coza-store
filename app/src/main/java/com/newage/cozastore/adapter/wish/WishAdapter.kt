package com.newage.cozastore.adapter.wish

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newage.cozastore.R
import com.newage.cozastore.api.models.product.Item
import com.newage.cozastore.application.CozaStore
import com.newage.cozastore.helper.replace
import kotlinx.android.synthetic.main.item_product_wish_layout.view.*

class WishAdapter(private val context: Context?, private var productList: ArrayList<Item>, val moveBagListener: (Item) -> Unit, val removeListener: (Item) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_wish_layout, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindItems(productList[position])

        holder.moveWish.setOnClickListener { moveBagListener(productList[position]) }
        holder.removeWish.setOnClickListener { removeListener(productList[position]) }

    }

    override fun getItemCount(): Int { return productList.size }

    fun filterWishList(filterName:ArrayList<Item>){
        this.productList = filterName
        notifyDataSetChanged()
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view:FrameLayout = itemView.image_frame
        val moveWish:Button = itemView.move_to_bag_button
        val removeWish:Button = itemView.remove_wish_button

        @SuppressLint("SetTextI18n")
        fun bindItems(item: Item) {

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

            view.layoutParams.width = CozaStore().getScreenWidth()/3

        }



    }
}


