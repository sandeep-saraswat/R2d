package com.example.r2d

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View


class new : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       // val user: FirebaseUser? = auth?.currentUser
   /*     if (user != null) {
            finish()
            startActivity(Intent(this, DashboardActivity::class.java))
        }*/
    }


    fun login(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
//        String TextClassname = classname.getText().toString();
//        // starting our intent
//        Intent classintent = new Intent(this,SecondActivity.class);
//        classintent.putExtra("Classname",TextClassname);
//        startActivityForResult(classintent,request_code);
    }

    fun register(view: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }
}

