package com.example.r2d

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.r2d.autosmssender.MainActivity
import com.example.r2d.group.CreateGroupActivity
import com.example.r2d.template.AddTemplateActivity


class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    var firebasenameview: TextView? = null
    var toast: Button? = null
    private var addItems: CardView? = null
    private var deleteItems: CardView? = null
    private var scanItems: CardView? = null
    private var viewInventory: CardView? = null
    private var WhatsappMessaging: CardView? = null
    private var MobMessaging: CardView? = null
    private var createGroup: CardView? = null
    private var createTemplete: CardView? = null
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        firebasenameview = findViewById(R.id.firebasename)

        // this is for username to appear after login



    //    val resultemail = result.replace(".", "")
     //   firebasenameview!!.text = "Welcome, $resultemail"
        //        toast.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(dashboardActivity.this, users.getEmail(), Toast.LENGTH_SHORT).show();
//            }
//        });
        addItems = findViewById(R.id.addItems) as CardView?
        deleteItems = findViewById(R.id.deleteItems) as CardView?
        scanItems = findViewById(R.id.scanItems) as CardView?
        viewInventory = findViewById(R.id.viewInventory) as CardView?
        WhatsappMessaging=findViewById(R.id.whatsApp) as CardView?
        MobMessaging=findViewById(R.id.message) as CardView?
        createGroup=findViewById(R.id.createGroup) as CardView?
        createTemplete=findViewById(R.id.createTemplete) as CardView?
        addItems?.setOnClickListener(this)
        deleteItems?.setOnClickListener(this)
        scanItems?.setOnClickListener(this)
        viewInventory?.setOnClickListener(this)
        WhatsappMessaging?.setOnClickListener(this)
        MobMessaging?.setOnClickListener(this)
        createGroup?.setOnClickListener(this)
        createTemplete?.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        val i: Intent
        when (view.id) {
            R.id.addItems -> {
                i = Intent(this, AdditemActivity::class.java)
                startActivity(i)
            }
            R.id.deleteItems -> {
            /*    i = Intent(this, DeleteItemsActivity::class.java)
                startActivity(i)*/
            }
            R.id.scanItems -> {
                i = Intent(this, scanItemsActivity::class.java)
                startActivity(i)
            }
            R.id.viewInventory -> {
                i = Intent(this, viewInventoryActivity::class.java)
                startActivity(i)
            }
            R.id.whatsApp -> {
                Log.e("whatsapp","click")
                i = Intent(this, MainActivity::class.java)
                i.putExtra("from", "whatsapp");
                startActivity(i)
            }
            R.id.message -> {
                i = Intent(this, MainActivity::class.java)
                i.putExtra("from", "sms");
                startActivity(i)
            }
            R.id.createGroup -> {

                Log.e("create group","click")
                i = Intent(this, CreateGroupActivity::class.java)
                startActivity(i)

            }
            R.id.createTemplete -> {
               // implemente later
                Log.e("create templete","click")
                i = Intent(this, AddTemplateActivity::class.java)
                startActivity(i)
            }
            else -> {
            }
        }
    }

    // logout below
    private fun Logout() {

        finish()
        startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
        Toast.makeText(this@DashboardActivity, "LOGOUT SUCCESSFUL", Toast.LENGTH_SHORT).show()
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
}
