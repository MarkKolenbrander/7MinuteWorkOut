package com.markkolenbrander.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import java.text.SimpleDateFormat
import java.util.*

class FinishActivity : AppCompatActivity() {

    private lateinit var finishButton: Button
    private lateinit var toolbarFinish: androidx.appcompat.widget.Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        finishButton = findViewById(R.id.finish_button)
        toolbarFinish = findViewById(R.id.toolbar_finish_activity)

        setSupportActionBar(toolbarFinish)
        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
        toolbarFinish.setNavigationOnClickListener {
            onBackPressed()
        }


        finishButton.setOnClickListener {
            finish()
        }

        addDateToBase()

    }

    private fun addDateToBase(){
        val calander = Calendar.getInstance()
        val dateTime = calander.time
        Log.i("DATE:", "" + dateTime)

        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        val date = sdf.format(dateTime)

        val dbHandler = SqliteOpenHelper(this, null)
        dbHandler.addDate(date)
        Log.i("DATE: ", "Added")
    }

}