package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBmain(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "trace.db"
        private const val TABLE_NAME = "record"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val create_table = "CREATE TABLE $TABLE_NAME(id INTEGER PRIMARY KEY AUTOINCREMENT, code TEXT, date TEXT, time TEXT)"
        db?.execSQL(create_table)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldV: Int, newV: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}