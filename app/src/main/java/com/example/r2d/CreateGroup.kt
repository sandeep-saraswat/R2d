package com.example.r2d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*

class CreateGroup : AppCompatActivity(), View.OnClickListener {

    private var imgViewBack: ImageView? = null
    private var txtViewTitle: TextView? = null
    private var groupName_et: EditText? = null
    private var contact_et: EditText? = null
    private var if_proceed_btn: Button? = null

    private var groupName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        imgViewBack = findViewById(R.id.imgViewBack) as ImageView?
        txtViewTitle = findViewById(R.id.txtViewTitle) as TextView?
        groupName_et = findViewById(R.id.groupName_et) as EditText?
        contact_et = findViewById(R.id.contact_et) as EditText?
        if_proceed_btn = findViewById(R.id.if_proceed_btn) as Button?

        txtViewTitle?.text = "Create Group"

        imgViewBack?.setOnClickListener(this)
        if_proceed_btn?.setOnClickListener(this)
        // groupName_et
    // contact_et
    // if_proceed_btn
    }

    override fun onClick(v: View?) {

        when(v)
        {
            if_proceed_btn -> {
                groupName = groupName_et?.text.toString().trim()

                if(groupName.length == 0)
                {
                    Toast.makeText(this,"Please enter group name.",Toast.LENGTH_LONG).show()
                    return
                }

            }

            imgViewBack -> {
                finish()
            }

            else -> {
                Log.e("nothing","seleted")
            }
        }
    }
}