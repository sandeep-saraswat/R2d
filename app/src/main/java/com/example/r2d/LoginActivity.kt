package com.example.r2d

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity



class LoginActivity : AppCompatActivity() {
    private var Email: EditText? = null
    private var Password: EditText? = null
    private var Login: Button? = null
    private var passwordreset: TextView? = null
    private var passwordresetemail: EditText? = null
    private var progressBar: ProgressBar? = null

    private var processDialog: ProgressDialog? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Email = findViewById(R.id.emailSignIn) as EditText?
        Password = findViewById(R.id.password) as EditText?
        Login = findViewById(R.id.Login) as Button?
        passwordreset = findViewById(R.id.forgotpassword)
        passwordresetemail = findViewById(R.id.emailSignIn)
        progressBar = findViewById(R.id.progressbars) as ProgressBar?
        progressBar!!.visibility = View.GONE

        processDialog = ProgressDialog(this)
        Login!!.setOnClickListener {   startActivity(Intent(this@LoginActivity, DashboardActivity::class.java)) }
        passwordreset!!.setOnClickListener {  }
    }

 /*   fun resetpasword() {
        val resetemail = passwordresetemail!!.text.toString()
        if (resetemail.isEmpty()) {
            passwordresetemail!!.error = "It's empty"
            passwordresetemail!!.requestFocus()
            return
        }
        progressBar!!.visibility = View.VISIBLE

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this@LoginActivity,
                        "We have sent you instructions to reset your password!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Failed to send reset email!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                progressBar!!.visibility = View.GONE
            }
    }*/


}
