package com.example.r2d.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast

fun confirmationDialog(context:Context,tittle:String,msg:String,cb:(select:Boolean) -> Unit)
{
    val builder = AlertDialog.Builder(context)
    builder.setTitle(tittle)
    builder.setMessage(msg)
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

    builder.setPositiveButton(android.R.string.yes) { dialog, which ->

        cb(true)
    }

    builder.setNegativeButton(android.R.string.no) { dialog, which ->

        cb(false)
    }

    /*builder.setNeutralButton("Maybe") { dialog, which ->
        Toast.makeText(context,
            "Maybe", Toast.LENGTH_SHORT).show()
    }*/
    builder.show()
}