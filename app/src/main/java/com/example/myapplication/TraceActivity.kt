package com.example.myapplication

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class TraceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trace)

        val TraceArr = arrayListOf <TraceRecord>(
            TraceRecord("2022/03/27","14:10","1231 2332 3133"),
            TraceRecord("2022/03/27","14:20","1231 2332 3155"),
            TraceRecord("2022/03/27","14:30","1231 2332 3136"),
            TraceRecord("2022/03/27","14:40","1231 2332 3137"),
            TraceRecord("2022/03/27","14:50","1231 2332 3138"),
            TraceRecord("2022/03/27","14:10","1231 2332 3133"),
            TraceRecord("2022/03/27","14:20","1231 2332 3155"),
            TraceRecord("2022/03/27","14:30","1231 2332 3136"),
            TraceRecord("2022/03/27","14:40","1231 2332 3137"),
            TraceRecord("2022/03/27","14:50","1231 2332 3138"),
            TraceRecord("2022/03/27","14:40","1231 2332 9999")
                )

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
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
    }
}