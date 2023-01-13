package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
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
        recyclerView.adapter = TraceAdapter(TraceArr, this)
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
        recyclerView.adapter = TraceAdapter(TraceArr, this)
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
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val code = cursor.getString(cursor.getColumnIndex("code"))
                val date = cursor.getString(cursor.getColumnIndex("date"))
                val time = cursor.getString(cursor.getColumnIndex("time"))
                val tr = TraceRecord(id, date, time, code)
                TraceArr.add(tr)
            } while (cursor.moveToNext())
        }
    }
}