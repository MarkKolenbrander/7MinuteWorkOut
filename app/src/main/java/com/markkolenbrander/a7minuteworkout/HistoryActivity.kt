package com.markkolenbrander.a7minuteworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {

    private lateinit var toolbarHistory: Toolbar
    private lateinit var tvHistory: TextView
    private lateinit var rvHistory: RecyclerView
    private lateinit var tvNoDataAvailable: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        toolbarHistory = findViewById(R.id.toolbar_history_activity)
        tvHistory = findViewById(R.id.tvHistory)
        rvHistory = findViewById(R.id.rvHistory)
        tvNoDataAvailable = findViewById(R.id.tvNoDataAvailable)

        window.statusBarColor = ContextCompat.getColor(this,R.color.colorMain)

        setSupportActionBar(toolbarHistory)

        val actionBar = supportActionBar
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "HISTORY"
        }
        toolbarHistory.setNavigationOnClickListener {
            onBackPressed()
        }

        getAllCompletedDates()

    }

    private fun getAllCompletedDates(){
        val dbHandler = SqliteOpenHelper(this, null)
        val allCompletedDateList = dbHandler.getAllCompletedDatesList()

        if (allCompletedDateList.size > 0){
            tvHistory.visibility = View.VISIBLE
            rvHistory.visibility = View.VISIBLE
            tvNoDataAvailable.visibility = View.GONE

            rvHistory.layoutManager = LinearLayoutManager(this)
            val historyAdapter = HistoryAdapter(this, allCompletedDateList)
            rvHistory.adapter = historyAdapter
        }else{
            tvHistory.visibility = View.GONE
            rvHistory.visibility = View.GONE
            tvNoDataAvailable.visibility = View.VISIBLE
        }
    }

}