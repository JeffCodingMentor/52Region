package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn1 = findViewById<ImageButton>(R.id.imageButton1)
        btn1.setOnClickListener {
            val intent = Intent(this, PassportActivity::class.java)
            startActivity(intent)
        }
    }
}