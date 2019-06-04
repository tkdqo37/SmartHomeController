package com.example.bt_ko

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_password.*
import org.jetbrains.anko.startActivity

class PasswordActivity : AppCompatActivity() {
    var password = 1234



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)
                btnSubmit.setOnClickListener {
            if (txtPassword.text.toString().toInt() === password) {
                Toast.makeText(applicationContext, "접속 되었습니다.", Toast.LENGTH_SHORT).show()
                //(View.OnClickListener { bt.send("2", true) })
                startActivity<MainActivity>()

            } else {
                Toast.makeText(applicationContext, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT).show()
                txtPassword.setText("")
            }
        }

    }
}

