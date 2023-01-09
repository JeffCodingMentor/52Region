package com.example.myapplication

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
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

    companion object {
        private const val IMAGE_PICK_CODE = 1000
        private const val PERMISSION_CODE = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserInfoBinding.inflate((layoutInflater))
        setContentView(binding.root)
        sharedPref = getSharedPreferences("myPref", MODE_PRIVATE)
        initForm()

        binding.imgAvatar.setOnClickListener {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, Companion.PERMISSION_CODE);
                } else {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = "image/*"
                    startActivityForResult(intent, Companion.IMAGE_PICK_CODE)
                }
            } else {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, Companion.IMAGE_PICK_CODE)
            }
        }

        binding.edtPID.setOnFocusChangeListener {_, hasFocus ->
            if(!hasFocus){
                val str = binding.edtPID.text
                if(!str?.matches("^[A-Za-z][0-9]{9}$".toRegex())!!) {
                    Toast.makeText(this, R.string.PromptPID, Toast.LENGTH_SHORT).show()
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

        binding.btnSave.setOnClickListener {
            var valid = booleanArrayOf(false,false,false,false,false,false,false,false)

            valid[0]= binding.edtChName.text?.length!! > 0
            valid[1]= binding.edtEnName.text?.length!! > 0
            valid[2]= binding.edtPID.text?.matches("^[A-Za-z][0-9]{9}$".toRegex())!!
            valid[3]= binding.edtBday.text?.matches("^\\d{4}/\\d{2}/\\d{2}$".toRegex())!!
            valid[4]= binding.edtCNo1.text?.length == 4
            valid[5]= binding.edtCNo2.text?.length == 4
            valid[6]= binding.edtCNo3.text?.length == 4
            valid[7]= binding.edtCNo4.text?.length == 4

            if(valid.all{it}) {
                val editor = sharedPref.edit()
                editor.putString("chname",binding.edtChName.text.toString())
                editor.putString("enname",binding.edtEnName.text.toString())
                editor.putString("pid",binding.edtPID.text.toString())
                editor.putString("bday",binding.edtBday.text.toString())
                editor.putString("cno1",binding.edtCNo1.text.toString())
                editor.putString("cno2",binding.edtCNo2.text.toString())
                editor.putString("cno3",binding.edtCNo3.text.toString())
                editor.putString("cno4",binding.edtCNo4.text.toString())
                editor.putBoolean("male",binding.edtGender.checkedRadioButtonId == R.id.radio_male)
                editor.apply()
                finish()
                Toast.makeText(this, R.string.PromptSaveOK, Toast.LENGTH_SHORT).show()
            }
            else {
                Toast.makeText(this, R.string.PromotEmpty, Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        calendar.set(year, month, dayOfMonth)
        binding.edtBday.text = formatter.format(calendar.timeInMillis)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == Companion.IMAGE_PICK_CODE) {

            img_uri = data?.data!!
            binding.imgAvatar.setImageURI(img_uri)

            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? =
                contentResolver.query(img_uri, filePathColumn, null, null, null)
            if (cursor != null) {
                cursor.moveToFirst()
                val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
                val img_path: String = cursor.getString(columnIndex)
                cursor.close()

                Log.w("BindingEX", "uri: $img_uri")
                Log.w("BindingEX", "path: $img_path")

                val editor = sharedPref.edit()
                editor.putString("imguri", img_path)
                editor.apply()
            }
        }
    }

    private fun initForm() {
        val img_path = sharedPref.getString("imguri","")
        Log.w("UserInfo", "init str: $img_path")
        if(img_path != null) {
            if (img_path!="") {
//              img_uri = Uri.parse(img_path)    //沒有用!!
                img_uri = File(img_path).toUri() //要到手機裡開權限
                binding.imgAvatar.setImageURI(img_uri)
                Log.w("UserInfo", "init uri: $img_uri")
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

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            Companion.PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                        //permission from popup granted
                        val intent = Intent(Intent.ACTION_PICK)
                        intent.type = "image/*"
                        startActivityForResult(intent, Companion.IMAGE_PICK_CODE)
                } else {
                    //permission from popup denied
                    Toast.makeText(this, R.string.PromptPermDenied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}