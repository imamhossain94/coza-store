package com.newage.cozastore.application

import android.app.Application
import android.content.res.Resources

class CozaStore:Application(){

    companion object{
        var displayHeight:Int = 100
        var displayWidth:Int = 100
    }


    override fun onCreate() {
        super.onCreate()

        displayHeight = getScreenHeight()
        displayWidth = getScreenWidth()

    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }

    fun getScreenHeight(): Int {
        return Resources.getSystem().displayMetrics.heightPixels
    }
}