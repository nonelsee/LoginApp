package com.example.loginapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R
import com.example.loginapp.model.User
import com.example.loginapp.dao.UserDao

class LoginAct : AppCompatActivity() {

    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_view)

        // Khởi tạo UserDao để thao tác với database
        userDao = UserDao(this)

        // Kiểm tra và thêm user cố định vào database nếu chưa có
        if (userDao.searchUser("admin") == null) {
            val defaultUser = User(
                username = "admin",
                password = "1234",
                fullname = "Admin User",
                email = "admin@example.com",
                dob = "01/01/1990",
                gender = "Male"
            )
            userDao.addUser(defaultUser)
        }

        val usernameEditText = findViewById<EditText>(R.id.usernameEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)
        val loginButton = findViewById<Button>(R.id.loginButton)
        val errorTextView = findViewById<TextView>(R.id.errorTextView)

        // Xử lý sự kiện nút "Login"
        loginButton.setOnClickListener {
            val enteredUsername = usernameEditText.text.toString()
            val enteredPassword = passwordEditText.text.toString()

            // Đọc user từ database
            val user = userDao.searchUser(enteredUsername)

            // Kiểm tra đăng nhập
            if (user != null && user.password == enteredPassword) {
                val intent = Intent(this, MainAct::class.java)
                intent.putExtra("fullname", user.fullname)
                startActivity(intent)
            } else {
                // Hiển thị thông báo lỗi
                errorTextView.text = "Sai username hoặc password"
            }
        }
    }
}

