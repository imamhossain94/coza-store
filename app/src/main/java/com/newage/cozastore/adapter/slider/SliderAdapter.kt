package com.newage.cozastore.adapter.slider

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newage.cozastore.R
import com.newage.cozastore.api.models.Category
import com.newage.cozastore.api.models.Slider
import com.newage.cozastore.application.CozaStore
import kotlinx.android.synthetic.main.item_category_layout.view.*
import kotlinx.android.synthetic.main.item_image_slider_layout.view.*


class SliderAdapter(private val context: Context?, private var sliderList: ArrayList<Slider>, val clickListener: (Slider) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {

        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_image_slider_layout, parent, false))
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindItems(sliderList[position])
        holder.sliderImage.setOnClickListener { clickListener(sliderList[position]) }

    }

    override fun getItemCount(): Int { return sliderList.size }

    fun filterSliderList(filterSlider:ArrayList<Slider>){
        this.sliderList = filterSlider
        notifyDataSetChanged()
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view:FrameLayout = itemView.frame_layout
        val sliderImage: ImageView = itemView.slider_image

        @SuppressLint("SetTextI18n")
        fun bindItems(slider: Slider) {
            val title = itemView.slider_title
            val sliderGif = itemView.slider_gif


            Glide.with(itemView).load("http://cozastore.newagedevs.com/${slider.slider_image}").fitCenter().into(sliderImage)

            //view.layoutParams.width = CozaStore().getScreenWidth()/3+97
            view.layoutParams.height = (CozaStore().getScreenWidth()/2)+80

        }

    }
}





