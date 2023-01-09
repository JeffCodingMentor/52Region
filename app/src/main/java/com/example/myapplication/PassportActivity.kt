package com.example.myapplication

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import java.io.File

class PassportActivity : AppCompatActivity() {

    private lateinit var viewPager:ViewPager2
    private lateinit var iv1:ImageView
    private lateinit var iv2:ImageView
    private lateinit var iv3:ImageView
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passport)
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)

        initUserInfo()

        val btn1 = findViewById<CardView>(R.id.CardViewDoz1)
        val btn2 = findViewById<CardView>(R.id.CardViewDoz2)
        val btn3 = findViewById<CardView>(R.id.CardViewDoz3)
        val btnPlus1 = findViewById<ImageButton>(R.id.ButtonPlus1)
        val btnPlus2 = findViewById<ImageButton>(R.id.ButtonPlus2)
        val btnPlus3 = findViewById<ImageButton>(R.id.ButtonPlus3)

        initViewPager()

        btn1.setOnClickListener {
            viewPager.setCurrentItem(0, true)
        }
        btn2.setOnClickListener {
            viewPager.setCurrentItem(1, true)
        }
        btn3.setOnClickListener {
            viewPager.setCurrentItem(2, true)
        }
        btnPlus1.setOnClickListener {
            viewPager.setCurrentItem(0, true)
        }
        btnPlus2.setOnClickListener {
            viewPager.setCurrentItem(1, true)
        }
        btnPlus3.setOnClickListener {
            viewPager.setCurrentItem(2, true)
        }

        val btnBack = findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            finish()
//            onBackPressed();
        }
        val btnPinfo = findViewById<ImageView>(R.id.imgPersonalInfo)
        btnPinfo.setOnClickListener {
            val intent = Intent(this, UserInfoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        initUserInfo()
    }

    private fun changeColor() {
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

    private fun initViewPager() {
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
    }

    private fun initUserInfo() {
        val img_path = sharedPref.getString("imguri","")
        val chname = sharedPref.getString("chname","")
        val enname = sharedPref.getString("enname","")
        val bday = sharedPref.getString("bday","")

        if(img_path != null) {
            if (img_path!="") {
                val imgPinfo = findViewById<ImageView>(R.id.imgPersonalInfo)
                val img_uri = File(img_path).toUri() //要到手機裡開權限
                imgPinfo.setImageURI(img_uri)
            }
        }

        val tv_Name = findViewById<TextView>(R.id.textViewName)
        tv_Name.text = chname+" "+enname //TODO:中英文名顯示要分開
        val tv_bday = findViewById<TextView>(R.id.textViewBirthDay)
        tv_bday.text = bday
    }
}