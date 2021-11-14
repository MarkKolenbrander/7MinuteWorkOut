package com.markkolenbrander.a7minuteworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private lateinit var llStart: LinearLayout
    private lateinit var llBMI: LinearLayout
    private lateinit var llHistory: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        window.statusBarColor = ContextCompat.getColor(this,R.color.colorMain)

        llStart = findViewById(R.id.llStart)
        llBMI = findViewById(R.id.llBMI)
        llHistory = findViewById(R.id.llHistory)

        llStart.setOnClickListener {
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        llBMI.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }

        llHistory.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }



}