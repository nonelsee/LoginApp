package com.example.loginapp.activity

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R
import com.example.loginapp.dao.UserDao
import com.example.loginapp.model.User

class EditUserAct : AppCompatActivity() {

    private lateinit var userDao: UserDao
    private lateinit var originalUsername: String
    private lateinit var currentUser: User
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var fullnameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var dobPicker: DatePicker
    private lateinit var genderSpinner: Spinner
    private lateinit var saveButton: Button
    private lateinit var cancelButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_user_view)

        // Khởi tạo các view
        usernameEditText = findViewById(R.id.usernameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        fullnameEditText = findViewById(R.id.fullnameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        dobPicker = findViewById(R.id.dobPicker)
        genderSpinner = findViewById(R.id.genderSpinner)
        saveButton = findViewById(R.id.saveButton)
        cancelButton = findViewById(R.id.cancelButton)

        // Khởi tạo UserDao
        userDao = UserDao(this)

        // Lấy dữ liệu user từ Intent
        originalUsername = intent.getStringExtra("username") ?: return

        // Tìm kiếm user trong database
        currentUser = userDao.searchUser(originalUsername) ?: return

        // Hiển thị thông tin user hiện tại lên giao diện
        usernameEditText.setText(currentUser.username)
        passwordEditText.setText(currentUser.password)
        fullnameEditText.setText(currentUser.fullname)
        emailEditText.setText(currentUser.email)

        // Đặt giá trị cho DatePicker từ ngày sinh của user
        val dobParts = currentUser.dob.split("/")
        if (dobParts.size == 3) {
            val day = dobParts[0].toInt()
            val month = dobParts[1].toInt() - 1 // DatePicker tháng bắt đầu từ 0
            val year = dobParts[2].toInt()
            dobPicker.updateDate(year, month, day)
        }

        // Cập nhật thông tin user
        saveButton.setOnClickListener {
            val updatedUser = User(
                id = currentUser.id,  // Kiểm tra ID của user
                username = usernameEditText.text.toString(),
                password = passwordEditText.text.toString(),
                fullname = fullnameEditText.text.toString(),
                email = emailEditText.text.toString(),
                dob = "${dobPicker.dayOfMonth}/${dobPicker.month + 1}/${dobPicker.year}",
                gender = genderSpinner.selectedItem?.toString() ?: "Unknown"
            )

            // Thêm log để kiểm tra thông tin user
            Log.d("EditUserAct", "Updating User: ${updatedUser.username}, ID: ${updatedUser.id}")

            val rowsAffected = userDao.updateUser(updatedUser)
            if (rowsAffected > 0) {
                Log.d("EditUserAct", "User updated successfully!")
                finish()  // Quay về màn hình trước sau khi lưu
            } else {
                Log.e("EditUserAct", "Failed to update user.")
            }
        }

        // Hủy bỏ chỉnh sửa
        cancelButton.setOnClickListener {
            finish()  // Quay về màn hình trước
        }
    }
}
