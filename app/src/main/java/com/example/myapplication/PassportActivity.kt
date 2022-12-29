package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class PassportActivity : AppCompatActivity() {

    private lateinit var viewPager:ViewPager2
    private lateinit var iv1:ImageView
    private lateinit var iv2:ImageView
    private lateinit var iv3:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport)

//        var btn1 = findViewById<Button>(R.id.button1)
//        var btn2 = findViewById<Button>(R.id.button2)
//        var btn3 = findViewById<Button>(R.id.button3)

        viewPager = findViewById(R.id.view_Pager)
        iv1 = findViewById(R.id.iv1)
        iv2 = findViewById(R.id.iv2)
        iv3 = findViewById(R.id.iv3)

        viewPager.apply {
            clipChildren = false
            clipToPadding = false
            offscreenPageLimit = 3
            (getChildAt(0) as RecyclerView).overScrollMode =
                RecyclerView.OVER_SCROLL_NEVER
        }

        val demoData = arrayListOf(
            "yes",
            "yes",
            "no"
        )

        viewPager.adapter = CarouselRVAdapter(demoData)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                changeColor()
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                changeColor()
                super.onPageScrollStateChanged(state)
            }

        })

//        btn1.setOnClickListener {
//            viewPager.setCurrentItem(0, true)
//        }
//        btn2.setOnClickListener {
//            viewPager.setCurrentItem(1, true)
//        }
//        btn3.setOnClickListener {
//            viewPager.setCurrentItem(2, true)
//        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
//            onBackPressed();
        }
    }

    fun changeColor()
    {
        when(viewPager.currentItem)
        {
            0->
            {
                iv1.setBackgroundResource(R.color.active)
                iv2.setBackgroundResource(R.color.grey)
                iv3.setBackgroundResource(R.color.grey)
            }
            1->
            {
                iv1.setBackgroundResource(R.color.grey)
                iv2.setBackgroundResource(R.color.active)
                iv3.setBackgroundResource(R.color.grey)
            }
            2->
            {
                iv1.setBackgroundResource(R.color.grey)
                iv2.setBackgroundResource(R.color.grey)
                iv3.setBackgroundResource(R.color.active)
            }
        }
    }
}