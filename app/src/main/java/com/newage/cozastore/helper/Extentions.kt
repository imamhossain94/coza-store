package com.newage.cozastore.helper

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.google.gson.GsonBuilder
import com.newage.cozastore.R
import com.newage.cozastore.api.models.Category
import com.newage.cozastore.api.models.Slider
import com.newage.cozastore.api.models.product.Item
import es.dmoral.toasty.Toasty
import java.lang.reflect.Type


/*var sliderList:ArrayList<Slider> = ArrayList()
var categoryList:ArrayList<Category> = ArrayList()
var productList:ArrayList<Item> = ArrayList()*/


fun Activity.switchIntent(activity: Activity){
    startActivity(Intent(this, activity::class.java))
}

fun Activity.refreshIntent(intent: Intent){
    finish()
    startActivity(intent)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T> Context.openActivity(it: Class<T>, extras: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(extras))
    startActivity(intent)
}

fun Context.toast(message: CharSequence) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Context.toastySuccess(message: CharSequence) = Toasty.success(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyError(message: CharSequence) = Toasty.error(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyInfo(message: CharSequence) = Toasty.info(this, message, Toast.LENGTH_SHORT, true).show()
fun Context.toastyWarning(message: CharSequence) = Toasty.warning(this, message, Toast.LENGTH_SHORT, true).show()


fun <T> outputRes(body:String, type: Type):T {
    return GsonBuilder().create().fromJson(body, type)
}

fun String.replace(vararg replacements: Pair<String, String>): String {
    var result = this
    replacements.forEach { (l, r) -> result = result.replace(l, r) }
    return result
}

fun View.getActivity(): Activity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is Activity) {
            return context
        }
        context = (context as ContextWrapper).baseContext
    }
    return null
}

lateinit var alertDialog:AlertDialog

//info dialogue
@SuppressLint("SetTextI18n")
fun Activity.showLoadingDialog() {

    val dialogBuilder = AlertDialog.Builder(this)
    val inflater = this.layoutInflater
    @SuppressLint("InflateParams")
    val dialogView = inflater.inflate(R.layout.dialogue_loading, null)
    dialogBuilder.setView(dialogView)

/*    val routineView = dialogView.containers
    val crossButton = dialogView.close_button

*/

    alertDialog = dialogBuilder.create()
   // val animPopUp = AnimationUtils.loadAnimation(this, R.anim.popupanim)
   // routineView.startAnimation(animPopUp)

    alertDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    alertDialog.setCanceledOnTouchOutside(false)
    alertDialog.show()

}

fun Activity.hideLoadingDialog() {
    alertDialog.dismiss()
}


fun blinkingView(view: View, tracking: Boolean) {
    val anim = AlphaAnimation(0.0f, 1.0f)
    anim.duration = 1000
    anim.startOffset = 20
    anim.repeatMode = Animation.REVERSE
    //anim.repeatCount = Animation.INFINITE
    anim.repeatCount = 0
    if (tracking) {
        view.startAnimation(anim)
    } else {
        view.clearAnimation()
    }
}


