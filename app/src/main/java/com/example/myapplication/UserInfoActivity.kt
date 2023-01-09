package com.example.myapplication

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUserInfoBinding
import java.text.SimpleDateFormat
import java.util.*

class UserInfoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding:ActivityUserInfoBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate((layoutInflater))
        setContentView(binding.root)

        binding.edtPID.setOnFocusChangeListener {_, hasFocus ->
            if(!hasFocus){
                val str = binding.edtPID.text
                if(!str?.matches("^[A-Za-z][0-9]{9}$".toRegex())!!) {
                    Toast.makeText(this, "請輸入正確身分證號碼", Toast.LENGTH_SHORT).show()
                }
            }

        }

        binding.edtBday.setOnClickListener {
            val str = binding.edtBday.text
            val strLst: List<String> = str.split("/")
            Log.w("UserInfo", "$strLst")
            if (strLst.size==3) {
                val yy=strLst[0].toInt()
                val mm=strLst[1].toInt()-1
                val dd=strLst[2].toInt()
                DatePickerDialog(this, this, yy, mm, dd).show()
            }
            else {
                DatePickerDialog(this, this, 2023, 0, 1).show()
            }
        }

        binding.edtCNo2.isEnabled = binding.edtCNo2.text?.length ==4
        binding.edtCNo3.isEnabled = binding.edtCNo3.text?.length ==4
        binding.edtCNo4.isEnabled = binding.edtCNo4.text?.length ==4

        binding.edtCNo1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                binding.edtCNo2.isEnabled = s?.length ==4
            }
        })
        binding.edtCNo2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                binding.edtCNo3.isEnabled = s?.length ==4
            }
        })
        binding.edtCNo3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                binding.edtCNo4.isEnabled = s?.length ==4
            }
        })

        binding.btnCancel.setOnClickListener {
            finish()
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        calendar.set(year, month, dayOfMonth)
        binding.edtBday.text = formatter.format(calendar.timeInMillis)
    }
}