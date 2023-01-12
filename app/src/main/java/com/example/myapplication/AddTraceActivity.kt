package com.example.myapplication

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TimePicker
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class AddTraceActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private lateinit var btnDate : Button
    private lateinit var btnTime : Button
    private lateinit var edtCode : EditText
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_trace)

        btnDate = findViewById<Button>(R.id.edt_Date)
        btnDate.setOnClickListener {
            val yy = calendar.get(Calendar.YEAR)
            val mm = calendar.get(Calendar.MONTH)
            val dd = calendar.get(Calendar.DAY_OF_MONTH)
            DatePickerDialog(this, this, yy, mm, dd).show()
        }

        btnTime = findViewById<Button>(R.id.edt_Time)
        btnTime.setOnClickListener {
            val hh = calendar.get(Calendar.HOUR_OF_DAY)
            val mm = calendar.get(Calendar.MINUTE)
            TimePickerDialog(this, this, hh, mm, false).show()
        }

        edtCode = findViewById<EditText>(R.id.edt_Code)

        val btnSave = findViewById<Button>(R.id.btn_Save)
        btnSave.setOnClickListener {
            var OK = true
            if(edtCode.text.length<10)
            {
                Toast.makeText(this, "Code not long enough", Toast.LENGTH_SHORT).show()
                OK = OK && false
            }
            if(btnDate.text.isEmpty())
            {
                Toast.makeText(this, "Date not set", Toast.LENGTH_SHORT).show()
                OK = OK && false
            }
            if(btnTime.text.isEmpty())
            {
                Toast.makeText(this, "Time not set", Toast.LENGTH_SHORT).show()
                OK = OK && false
            }
            if(OK){
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        val btnCancel = findViewById<Button>(R.id.btn_cancel)
        btnCancel.setOnClickListener { finish() }
    }

    override fun onDateSet(v: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        calendar.set(year, month, dayOfMonth)
        btnDate.text = formatter.format(calendar.timeInMillis)
    }

    override fun onTimeSet(v: TimePicker?, hourOfDay: Int, minute: Int) {
        val formatter = SimpleDateFormat("KK:mm aaa", Locale.US)
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        btnTime.text = formatter.format(calendar.time)
    }
}