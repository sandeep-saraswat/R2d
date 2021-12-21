package com.example.r2d

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity



class deleteItemsActivity : AppCompatActivity() {

    var scantodelete: Button? = null
    var deletebtn: Button? = null

   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_items)

        resultdeleteview = findViewById(R.id.barcodedelete)
        scantodelete = findViewById(R.id.buttonscandelete)
        deletebtn = findViewById(R.id.deleteItemToTheDatabasebtn)
        scantodelete!!.setOnClickListener {
          /*  startActivity(
              *//*  Intent(
                    getApplicationContext(),
                    //ScanCodeActivitydel::class.java
                )*//*
            )*/
        }
        deletebtn!!.setOnClickListener {  }
    }

   /* fun deletefrmdatabase() {
        val deletebarcodevalue = resultdeleteview!!.text.toString()

        val finaluser = users!!.email
        val resultemail = finaluser!!.replace(".", "")
        if (!TextUtils.isEmpty(deletebarcodevalue)) {
            databaseReference!!.child(resultemail).child("Items").child(deletebarcodevalue)
                .removeValue()
            Toast.makeText(this@deleteItemsActivity, "Item is Deleted", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this@deleteItemsActivity, "Please scan Barcode", Toast.LENGTH_SHORT)
                .show()
        }
    }*/

    companion object {
        var resultdeleteview: TextView? = null
    }
}
