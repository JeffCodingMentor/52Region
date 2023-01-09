package com.example.myapplication

import android.app.DatePickerDialog
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.core.net.toUri
import com.example.myapplication.databinding.ActivityUserInfoBinding
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class UserInfoActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding:ActivityUserInfoBinding
    private val calendar = Calendar.getInstance()
    private lateinit var sharedPref: SharedPreferences
    private lateinit var img_uri: Uri


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate((layoutInflater))
        setContentView(binding.root)
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        initForm()

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

    private fun initForm() {
        val img_path = sharedPref.getString("imguri","")
        Log.w("BindingEX", "init str: $img_path")
        if(img_path != null) {
            if (img_path!="") {
//              img_uri = Uri.parse(img_path)    //沒有用!!
                img_uri = File(img_path).toUri() //要到手機裡開權限
                binding.imgAvatar.setImageURI(img_uri)
                Log.w("BindingEX", "init uri: $img_uri")
            }
        }

        if(sharedPref.getBoolean("male",true)) {
            binding.radioMale.isChecked = true
        } else {
            binding.radioFemale.isChecked = true
        }

        binding.edtChName.setText(sharedPref.getString("chname",""))
        binding.edtEnName.setText(sharedPref.getString("enname",""))
        binding.edtPID.setText(sharedPref.getString("pid",""))
        binding.edtBday.text = sharedPref.getString("bday","")
        binding.edtCNo1.setText(sharedPref.getString("cno1",""))
        binding.edtCNo2.setText(sharedPref.getString("cno2",""))
        binding.edtCNo3.setText(sharedPref.getString("cno3",""))
        binding.edtCNo4.setText(sharedPref.getString("cno4",""))
    }

}