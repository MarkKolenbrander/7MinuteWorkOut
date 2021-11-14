package com.markkolenbrander.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat

class splashActivity : AppCompatActivity() {

    private lateinit var llSplash : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        window.statusBarColor = ContextCompat.getColor(this,R.color.colorMain)

        llSplash = findViewById(R.id.ll_splash_activity)

        llSplash.alpha = 0f
        llSplash.animate().setDuration(5000).alpha(1f).withEndAction {
            val i = Intent (this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}