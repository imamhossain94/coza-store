package com.newage.cozastore.fragments

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.google.gson.reflect.TypeToken
import com.newage.cozastore.R
import com.newage.cozastore.adapter.catagory.CategoryAdapter
import com.newage.cozastore.adapter.product.ProductAdapter
import com.newage.cozastore.adapter.slider.SliderAdapter
import com.newage.cozastore.api.models.Category
import com.newage.cozastore.api.models.Slider
import com.newage.cozastore.api.models.product.Item
import com.newage.cozastore.api.services.ApiBuilder
import com.newage.cozastore.api.services.ApiService
import com.newage.cozastore.helper.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    companion object{

        var sliderList:ArrayList<Slider> = ArrayList()
        var categoryList:ArrayList<Category> = ArrayList()
        var productList:ArrayList<Item> = ArrayList()

    }

    private lateinit var swipeLayout: SwipeRefreshLayout

    private lateinit var sliderAdapter: SliderAdapter
    private lateinit var sliderRecyclerView: RecyclerView

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var categoryRecyclerView: RecyclerView

    private lateinit var recentAdapter: ProductAdapter
    private lateinit var recentProductRecyclerView: RecyclerView
    private lateinit var productOverviewRecyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {

            sliderRecyclerView = it.findViewById(R.id.slider_recycler_view)
            categoryRecyclerView = it.findViewById(R.id.catagory_recycler_view)
            recentProductRecyclerView = it.findViewById(R.id.recent_product_recycler_view)
            productOverviewRecyclerView  = it.findViewById(R.id.product_overview_recycler_view)

            swipeLayout = it.findViewById(R.id.swipeContainer)
        }

        sliderRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        SnapHelper().attachToRecyclerView(sliderRecyclerView)

        categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        recentProductRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        productOverviewRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)


/*        activity?.displayMetrics()?.run {
            h = heightPixels
            w = widthPixels
*//*            sliderView.layoutParams.width = w
            sliderView.layoutParams.height = (w/2)+100*//*
        }*/

        sliderAdapter = SliderAdapter(context, sliderList, clickListener = {
            sliderItemClickListeners(it)
        })

        categoryAdapter = CategoryAdapter(context, categoryList, clickListener = {
            categoryItemClickListeners(it)
        })

        recentAdapter = ProductAdapter(context, productList, DatabaseHelper(requireContext()).getWishList(), productListener = {
            productItemViewClickListeners(it)
        }, wishListener = {
            productItemWishClickListeners(it)
        })

        sliderRecyclerView.adapter = sliderAdapter
        categoryRecyclerView.adapter = categoryAdapter
        recentProductRecyclerView.adapter = recentAdapter
        productOverviewRecyclerView.adapter = recentAdapter

        pullDownRefreshListeners()

    }

    private fun Activity.displayMetrics():DisplayMetrics {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        return dm
    }

    private fun pullDownRefreshListeners(){
        swipeLayout.setOnRefreshListener(OnRefreshListener {

            activity!!.showLoadingDialog()

            loadSlider()
            loadCategory()
            loadProduct()
            Handler().postDelayed({
                swipeLayout.isRefreshing = false
            }, 200)
        })
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        if(sliderList.isEmpty() || categoryList.isEmpty() || productList.isEmpty()){

            activity!!.showLoadingDialog()

            loadSlider()
            loadCategory()
            loadProduct()
        }
    }

    private fun loadSlider() {
        val destinationService = ApiBuilder.buildService(ApiService::class.java)
        val requestCall = destinationService.getSliderList()
        requestCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseObj = JSONObject(response.body()!!.string())
                    val category = responseObj.getJSONArray("sliders")
                    sliderList = outputRes(category.toString(), object : TypeToken<ArrayList<Slider>>() {}.type) as ArrayList<Slider>
                    sliderAdapter.filterSliderList(sliderList)
                }else{
                    requireContext().toastyError(response.toString())
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                requireContext().toastyError(t.toString())
            }
        })
    }

    private fun loadCategory() {
        val destinationService = ApiBuilder.buildService(ApiService::class.java)
        val requestCall = destinationService.getCategoryList()
        requestCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseObj = JSONObject(response.body()!!.string())
                    val category = responseObj.getJSONArray("data")
                    categoryList = outputRes(category.toString(), object : TypeToken<ArrayList<Category>>() {}.type) as ArrayList<Category>
                    categoryAdapter.filterCategoryList(categoryList)
                }else{
                    requireContext().toastyError(response.toString())
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                requireContext().toastyError(t.toString())
            }
        })
    }

    private fun loadProduct() {
        val destinationService = ApiBuilder.buildService(ApiService::class.java)
        val requestCall = destinationService.getProductList()
        requestCall.enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val responseObj = JSONObject(response.body()!!.string())
                    //val product = responseObj.getJSONObject("products")
                    val item = responseObj.getJSONArray("data")
                    productList = outputRes(item.toString(), object : TypeToken<ArrayList<Item>>() {}.type) as ArrayList<Item>
                    recentAdapter .filterProductList(productList,DatabaseHelper(requireContext()).getWishList())
                }else{
                    requireContext().toastyError(response.toString())
                }
                activity!!.hideLoadingDialog()
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                activity!!.hideLoadingDialog()
                requireContext().toastyError(t.toString())
            }
        })
    }



    private fun sliderItemClickListeners(image:Slider){

    }

    private fun categoryItemClickListeners(category:Category){

    }

    private fun productItemViewClickListeners(product:Item){

    }

    private fun productItemWishClickListeners(product:Item){
        if(DatabaseHelper(requireContext()).getWishList().any { h -> h.id == product.id }){
            DatabaseHelper(requireContext()).removeWishById(product.id.toString())
            recentAdapter .filterProductList(productList,DatabaseHelper(requireContext()).getWishList())
        }else{
            DatabaseHelper(requireContext()).saveWish(product)
            recentAdapter .filterProductList(productList,DatabaseHelper(requireContext()).getWishList())
        }
    }

}