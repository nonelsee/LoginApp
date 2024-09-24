package com.example.loginapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.loginapp.R
import com.example.loginapp.model.User
import com.example.loginapp.dao.UserDao

class SearchUserAct : AppCompatActivity() {

    private lateinit var userListLayout: LinearLayout
    private lateinit var searchEditText: EditText
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_user_view)

        // Khởi tạo các view
        userListLayout = findViewById(R.id.userListLayout)
        searchEditText = findViewById(R.id.searchEditText)
        userDao = UserDao(this)

        // Hiển thị toàn bộ user khi khởi chạy
        displayUsers(userDao.getAllUsers())

        searchEditText.addTextChangedListener { text ->
            val searchText = text.toString()
            val filteredUsers = userDao.getAllUsers().filter {
                it.username.contains(searchText, true) ||
                        it.fullname.contains(searchText, true) ||
                        it.email.contains(searchText, true)
            }
            displayUsers(filteredUsers)
        }
    }

    // Hàm hiển thị danh sách user
    private fun displayUsers(users: List<User>) {
        userListLayout.removeAllViews() // Xóa các view cũ trước khi hiển thị danh sách mới

        for (user in users) {
            // Tạo view cho từng user từ layout user_item.xml
            val userView = layoutInflater.inflate(R.layout.user_item, null)

            // Gán thông tin cho từng user
            val usernameTextView = userView.findViewById<TextView>(R.id.usernameTextView)
            val fullnameTextView = userView.findViewById<TextView>(R.id.fullnameTextView)
            val emailTextView = userView.findViewById<TextView>(R.id.emailTextView)
            val editButton = userView.findViewById<Button>(R.id.editButton)
            val deleteButton = userView.findViewById<Button>(R.id.deleteButton)

            usernameTextView.text = user.username
            fullnameTextView.text = user.fullname
            emailTextView.text = user.email

            // Nút "Edit" để chỉnh sửa user
            editButton.setOnClickListener {
                val intent = Intent(this, EditUserAct::class.java)
                intent.putExtra("username", user.username)
                startActivity(intent)
            }

            // Nút "Delete" để xóa user
            deleteButton.setOnClickListener {
                userDao.deleteUser(user.id)
                displayUsers(userDao.getAllUsers()) // Cập nhật lại danh sách sau khi xóa
            }

            // Thêm view user vào LinearLayout
            userListLayout.addView(userView)
        }
    }
}
