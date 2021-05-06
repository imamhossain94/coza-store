package com.newage.cozastore.adapter.catagory

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.newage.cozastore.R
import com.newage.cozastore.api.models.Category
import com.newage.cozastore.api.models.product.Item
import com.newage.cozastore.application.CozaStore
import kotlinx.android.synthetic.main.item_category_layout.view.*

class CategoryAdapter(private val context: Context?, var categoryList: ArrayList<Category>, val clickListener: (Category) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_category_layout, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyViewHolder).bindItems(categoryList[position])
        holder.button.setOnClickListener { clickListener(categoryList[position]) }
    }

    override fun getItemCount(): Int { return categoryList.size }

    fun filterCategoryList(filterCategory:ArrayList<Category>){
        this.categoryList = filterCategory
        notifyDataSetChanged()
    }

    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val view:CardView = itemView.catagory_view
        val button: ImageView = itemView.category_image

        @SuppressLint("SetTextI18n")
        fun bindItems(category: Category) {
            val title = itemView.category_title
            val subTitle = itemView.category_sub_title

            title.text = category.category_name
            subTitle.text = category.category_description

            Glide.with(itemView).load("http://cozastore.newagedevs.com/${category.category_image}").fitCenter().into(button)



            if(CozaStore().getScreenHeight() <= 1080){

            }

            view.layoutParams.width = CozaStore().getScreenWidth()/3+97
            view.layoutParams.height = (CozaStore().getScreenWidth()/3)-48

        }



    }
}

/*
class CategoryModel(var id:Int,
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
*/











