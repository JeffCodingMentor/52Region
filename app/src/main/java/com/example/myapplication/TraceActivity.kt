package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TraceActivity : AppCompatActivity() {

    private val TraceArr: ArrayList<TraceRecord> = ArrayList()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trace)

        initData()

        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = TraceAdapter(TraceArr)
        recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
        }

        val btnAddTrace = findViewById<ImageButton>(R.id.btn_addtrace)
        btnAddTrace.setOnClickListener {
            val intent = Intent(this, AddTraceActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        initData()
        recyclerView.adapter = TraceAdapter(TraceArr)
    }

    @SuppressLint("Range")
    private fun initData() {
        val db = DBmain(this)
        val cursor: Cursor?
        TraceArr.clear()

        try {
            cursor = db.readableDatabase.rawQuery("SELECT * FROM record", null)
        } catch (e: java.lang.Exception) {
            return
        }

        if(cursor.moveToFirst()){
            do {
                val code = cursor.getString(cursor.getColumnIndex("code"))
                val date = cursor.getString(cursor.getColumnIndex("date"))
                val time = cursor.getString(cursor.getColumnIndex("time"))
                val tr = TraceRecord(date, time, code)
                TraceArr.add(tr)
            } while (cursor.moveToNext())
        }
    }
}