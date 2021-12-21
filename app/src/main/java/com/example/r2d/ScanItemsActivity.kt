package com.example.r2d

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView



class scanItemsActivity : AppCompatActivity() {

    var scantosearch: ImageButton? = null
    var searchbtn: Button? = null
    var adapter: Adapter? = null
    var mrecyclerview: RecyclerView? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_items)


       // val resultemail = finaluser!!.replace(".", "")

        resultsearcheview = findViewById(R.id.searchfield)
        scantosearch = findViewById(R.id.imageButtonsearch)
        searchbtn = findViewById(R.id.searchbtnn)
        mrecyclerview = findViewById(R.id.recyclerViews)
        val manager = LinearLayoutManager(this)
        mrecyclerview?.layoutManager = manager
        mrecyclerview?.setHasFixedSize(true)
        mrecyclerview?.layoutManager = LinearLayoutManager(this)
        scantosearch!!.setOnClickListener {
           /* startActivity(
                Intent(
                    getApplicationContext(),
                    ScanCodeActivitysearch::class.java
                )
            )*/
        }
        searchbtn!!.setOnClickListener {
            val searchtext = resultsearcheview!!.text.toString()
           // firebasesearch(searchtext)
        }
    }

   /* fun firebasesearch(searchtext: String) {
        val firebaseSearchQuery =
            mdatabaseReference!!.orderByChild("itembarcode").startAt(searchtext)
                .endAt(searchtext + "\uf8ff")
        val firebaseRecyclerAdapter: FirebaseRecyclerAdapter<Items, UsersViewHolder> =
            object : FirebaseRecyclerAdapter<Items, UsersViewHolder>(
                Items::class.java,
                R.layout.list_layout,
                UsersViewHolder::class.java,
                firebaseSearchQuery
            ) {
                override fun populateViewHolder(
                    viewHolder: UsersViewHolder,
                    model: Items,
                    position: Int
                ) {
                    viewHolder.setDetails(
                        applicationContext,
                        model.itembarcode,
                        model.itemcategory,
                        model.itemname,
                        model.itemprice
                    )
                }
            }
        mrecyclerview?.setAdapter(firebaseRecyclerAdapter)
    }*/

    class UsersViewHolder(var mView: View) : RecyclerView.ViewHolder(
        mView
    ) {
        fun setDetails(
            ctx: Context?,
            itembarcode: String?,
            itemcategory: String?,
            itemname: String?,
            itemprice: String?
        ) {
            val item_barcode = mView.findViewById<View>(R.id.viewitembarcode) as TextView
            val item_name = mView.findViewById<View>(R.id.viewitemname) as TextView
            val item_category = mView.findViewById<View>(R.id.viewitemcategory) as TextView
            val item_price = mView.findViewById<View>(R.id.viewitemprice) as TextView
            item_barcode.text = itembarcode
            item_category.text = itemcategory
            item_name.text = itemname
            item_price.text = itemprice
        }
    }

    companion object {
        var resultsearcheview: EditText? = null
    }
}
