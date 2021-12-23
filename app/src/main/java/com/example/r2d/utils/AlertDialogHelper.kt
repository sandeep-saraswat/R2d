package com.example.r2d.utils


import android.app.Dialog
import android.content.Context
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.adapter.ContactGroupAdapter
import com.example.r2d.adapter.SelectedContactAdapter
import com.example.r2d.database.ContactGroupData
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult

class AlertDialogHelper {


    companion object{

        @JvmStatic
        fun dialogShowList(context: Context,title:String?, list:ArrayList<ContactGroupData>, CB : (ContactGroupData) -> Unit) {

            val dialog = Dialog(context, android.R.style.Theme_Light_NoTitleBar_Fullscreen)
            //val dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(true)
            // val inflater = context.layoutInflater
            dialog.setContentView(R.layout.dialog_list)//
            // val view: View = inflater.inflate(R.layout.item_search_dialog, null)
            //view.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
            //view.setBackgroundColor(.resources(R.color.button_blue))
            // dialog.setContentView(view)

            val imgViewBack = dialog.findViewById<View>(R.id.imgViewBack) as ImageView?
            val txtViewTitle = dialog.findViewById<View>(R.id.txtViewTitle) as TextView?
            val recyclerViewList = dialog.findViewById<View>(R.id.recyclerViewList) as RecyclerView?
            val tvSelect = dialog.findViewById<View>(R.id.tvSelect) as TextView?
            val tvSelectAll = dialog.findViewById<View>(R.id.tvSelectAll) as TextView?


            txtViewTitle?.text = title


            // set list
            recyclerViewList?.layoutManager = GridLayoutManager(context, 1)
            var selectedContactAdapter = ContactGroupAdapter(context, list) {
                CB(it)
                dialog.dismiss()
            }

            recyclerViewList?.adapter = selectedContactAdapter



            dialog.setOnKeyListener { arg0, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss()
                    // changeBackgroundFilter()
                }
                true
            }


            imgViewBack?.setOnClickListener {
                dialog.dismiss()
            }


            tvSelect?.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        }

    }
}