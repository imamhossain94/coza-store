package com.newage.cozastore.activity.splash

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.annotation.RequiresApi
import com.newage.cozastore.R
import com.newage.cozastore.activity.AppBaseActivity
import com.newage.cozastore.helper.switchIntent

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        @RequiresApi(Build.VERSION_CODES.M)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

        Handler().postDelayed({
            this.switchIntent(AppBaseActivity())
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        },2000)

    }

}