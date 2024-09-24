package com.example.loginapp.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R
import com.example.loginapp.dao.UserDao
import com.example.loginapp.model.User

class AddUserAct : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var fullnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobPicker: DatePicker
    private lateinit var genderSpinner: Spinner
    private lateinit var addButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_user_view)

        // Khởi tạo các view
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        fullnameEditText = findViewById(R.id.fullnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        dobPicker = findViewById(R.id.dobPicker)
        genderSpinner = findViewById(R.id.genderSpinner)
        addButton = findViewById(R.id.addButton)
        cancelButton = findViewById(R.id.cancelButton)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.gender_array,  // Mảng dữ liệu cho Spinner
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genderSpinner.adapter = adapter

        // Khởi tạo UserDao
        userDao = UserDao(this)

        // Xử lý khi nhấn nút "Add"
        addButton.setOnClickListener {
            val newUser = User(
                username = usernameEditText.text.toString(),
                password = passwordEditText.text.toString(),
                fullname = fullnameEditText.text.toString(),
                email = emailEditText.text.toString(),
                dob = "${dobPicker.dayOfMonth}/${dobPicker.month + 1}/${dobPicker.year}", // Cộng thêm 1 cho tháng
                gender = genderSpinner.selectedItem.toString()
            )

            // Thêm user vào database
            userDao.addUser(newUser)
            finish() // Quay về màn hình trước sau khi thêm user
        }

        // Xử lý khi nhấn nút "Cancel"
        cancelButton.setOnClickListener {
            finish() // Quay về màn hình trước
        }
    }
}
