package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.myapplication.databinding.ActivityUserInfoBinding

class UserInfoActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUserInfoBinding

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
}