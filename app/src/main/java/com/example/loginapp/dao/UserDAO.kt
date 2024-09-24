package com.example.loginapp.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.example.loginapp.model.User

class UserDao(context: Context) {

    private val dbHelper = DBHelper(context)

    // Thêm user vào database
    fun addUser(user: User): Long {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", user.username)
            put("password", user.password)
            put("fullname", user.fullname)
            put("email", user.email)
            put("dob", user.dob)
            put("gender", user.gender)
        }
        // Chèn user vào database, trả về ID của user mới thêm
        return db.insert("user_data", null, values)
    }

    // Tìm kiếm user theo username
    fun searchUser(username: String): User? {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query(
            "user_data", null, "username = ?", arrayOf(username),
            null, null, null
        )
        if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
                password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
                fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname")),
                email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                dob = cursor.getString(cursor.getColumnIndexOrThrow("dob")),
                gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
            )
            cursor.close()
            return user
        }
        cursor.close()
        return null
    }

    // Cập nhật thông tin user
    fun updateUser(user: User): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("username", user.username)
            put("password", user.password)
            put("fullname", user.fullname)
            put("email", user.email)
            put("dob", user.dob)
            put("gender", user.gender)
        }

        // Cập nhật user trong database dựa trên ID
        return db.update("user_data", values, "id = ?", arrayOf(user.id.toString()))
    }



    // Lấy tất cả user từ database
    fun getAllUsers(): MutableList<User> {
        val db: SQLiteDatabase = dbHelper.readableDatabase
        val cursor: Cursor = db.query("user_data", null, null, null, null, null, null)
        val userList = mutableListOf<User>()
        if (cursor.moveToFirst()) {
            do {
                val user = User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    username = cursor.getString(cursor.getColumnIndexOrThrow("username")),
                    password = cursor.getString(cursor.getColumnIndexOrThrow("password")),
                    fullname = cursor.getString(cursor.getColumnIndexOrThrow("fullname")),
                    email = cursor.getString(cursor.getColumnIndexOrThrow("email")),
                    dob = cursor.getString(cursor.getColumnIndexOrThrow("dob")),
                    gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"))
                )
                userList.add(user)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return userList
    }

    // Xóa user khỏi database dựa trên ID
    fun deleteUser(userId: Int): Int {
        val db: SQLiteDatabase = dbHelper.writableDatabase
        return db.delete("user_data", "id = ?", arrayOf(userId.toString()))
    }
}

