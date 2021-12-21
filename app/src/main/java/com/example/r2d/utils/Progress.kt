package com.example.r2d.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.WindowManager
import com.example.r2d.R


class Progress {

    companion object {
        internal var dialog: Dialog? = null

        fun start(context: Context) {
            dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null)
            dialog!!.setContentView(inflate)
            dialog!!.setCancelable(true)
            dialog!!.setCanceledOnTouchOutside(false)
            dialog!!.window!!.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT))
            try {
                dialog!!.show()
            } catch (e: WindowManager.BadTokenException) {
                e.printStackTrace()
            } catch (ex: IllegalArgumentException) {
                ex.printStackTrace()
            }

        }

        fun stop() {

            if (dialog != null)
                dialog!!.dismiss()
        }


    }
}