package com.example.loginapp.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.loginapp.R

class MainAct : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_view)

        val welcomeTextView = findViewById<TextView>(R.id.welcomeTextView)
        val addUserButton = findViewById<Button>(R.id.addUserButton)
        val searchUserButton = findViewById<Button>(R.id.searchUserButton)

        val fullname = intent.getStringExtra("fullname")
        welcomeTextView.text = "Xin chào $fullname"

        addUserButton.setOnClickListener {
            startActivity(Intent(this, AddUserAct::class.java))
        }

        searchUserButton.setOnClickListener {
            startActivity(Intent(this, SearchUserAct::class.java))
        }
    }
}
