package com.example.r2d

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class AdditemActivity : AppCompatActivity() {
    private var itemname: EditText? = null
    private var itemcategory: EditText? = null
    private var itemprice: EditText? = null
    private var quantity:EditText? = null

    var additemtodatabase: Button? = null
    private var db:AppDatabase?=null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_additem)

        resulttextview = findViewById(R.id.barcodeview)
        additemtodatabase = findViewById(R.id.additembuttontodatabase)
        itemname = findViewById(R.id.edititemname)
        itemcategory = findViewById(R.id.editcategory)
        itemprice = findViewById(R.id.editprice)
        quantity=  findViewById(R.id.quantity)
          db = Room.databaseBuilder(
            this@AdditemActivity,
            AppDatabase::class.java, "todo-list.db"
        ).build()
        additemtodatabase!!.setOnClickListener { additem() }
    }

    // addding item to databse
    private fun additem() {

        val itemnameValue = itemname!!.text.toString()
        val itemcategoryValue = itemcategory!!.text.toString()
        val itempriceValue = itemprice!!.text.toString()
        val itemquantity = quantity!!.text.toString()



       // val resultemail = finaluser!!.replace(".", "")
        if (itemquantity.isEmpty()) {
            quantity!!.error = "It's Empty"
            quantity!!.requestFocus()
            return
        }
        if (!TextUtils.isEmpty(itemnameValue) && !TextUtils.isEmpty(itemcategoryValue) && !TextUtils.isEmpty(
                itempriceValue
            )
        ) {

            val itemsData = ItemData("1",itemnameValue, itemcategoryValue, itempriceValue, itemquantity,"")
            GlobalScope.launch {
                db?.appDao()?.insertAll(itemsData)
                val  data = db?.appDao()?.getAllData()
                Toast.makeText(this@AdditemActivity, "$itemnameValue Added", Toast.LENGTH_SHORT).show()
                data?.forEach {
                    println(it)
                }
            }
            val intent = Intent(this@AdditemActivity, DashboardActivity::class.java)
            startActivity(intent)


        } else {
            Toast.makeText(this@AdditemActivity, "Please Fill all the fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // logout below
    private fun Logout() {
      //  firebaseAuth!!.signOut()
        finish()
        startActivity(Intent(this@AdditemActivity, LoginActivity::class.java))
        Toast.makeText(this@AdditemActivity, "LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logoutMenu -> {
                Logout()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        var resulttextview: TextView? = null
    }
}
