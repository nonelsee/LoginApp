package com.example.loginapp.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "user_database"
        private const val DATABASE_VERSION = 1

        private const val TABLE_USER = "user_data"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_FULLNAME = "fullname"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_DOB = "dob"
        private const val COLUMN_GENDER = "gender"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // Tạo bảng user_data
        val createTableQuery = """
            CREATE TABLE $TABLE_USER (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USERNAME TEXT,
                $COLUMN_PASSWORD TEXT,
                $COLUMN_FULLNAME TEXT,
                $COLUMN_EMAIL TEXT,
                $COLUMN_DOB TEXT,
                $COLUMN_GENDER TEXT
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)

//        // Thêm 1 user cố định vào database
//        val insertDefaultUserQuery = """
//            INSERT INTO $TABLE_USER ($COLUMN_USERNAME, $COLUMN_PASSWORD, $COLUMN_FULLNAME, $COLUMN_EMAIL, $COLUMN_DOB, $COLUMN_GENDER)
//            VALUES ('admin', '1234', 'Admin User', 'admin@example.com', '01/01/1990', 'Male')
//        """.trimIndent()
//
//        db?.execSQL(insertDefaultUserQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USER")
        onCreate(db)
    }
}
