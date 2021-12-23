package com.example.r2d.autosmssender

import android.Manifest
import com.example.r2d.utils.AlertDialogHelper.Companion.dialogShowList
import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import com.example.r2d.adapter.SelectedContactAdapter
import android.os.Bundle
import com.example.r2d.R
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import android.content.Intent
import com.example.r2d.wafflecopter.multicontactpicker.MultiContactPicker
import androidx.core.content.ContextCompat
import com.example.r2d.wafflecopter.multicontactpicker.LimitColumn
import com.example.r2d.autosmssender.MainActivity
import com.example.r2d.autosmssender.MySMSservice
import android.content.IntentFilter
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.widget.Toast
import com.example.r2d.utils.AlertDialogHelper
import android.app.Activity
import androidx.recyclerview.widget.GridLayoutManager
import android.content.BroadcastReceiver
import android.content.Context
import android.graphics.Color
import android.provider.Settings
import com.example.r2d.autosmssender.WhatAppAccessibilityService
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ContactGroupData
import com.karumi.dexter.listener.PermissionRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.StringBuilder
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private var edt_message: EditText? = null
    private var txt_number: EditText? = null
    private var txt_count: EditText? = null
    private var btn_manual: Button? = null
    private var btn_sms: Button? = null
    private var btn_whatsapp: Button? = null
    private var btn_choose: Button? = null
    private var button_choose_group: Button? = null
    private var button_choose_template: Button? = null
    private var btn_send: Button? = null
    private var rv_contact_list: RecyclerView? = null
    var results = ArrayList<ContactResult>()
    var selectedContactAdapter: SelectedContactAdapter? = null
    private var btn_whatsapp_each_word: Button? = null

    private var db: AppDatabase? = null

    var contactGroupDataList= ArrayList<ContactGroupData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massaging)
        edt_message = findViewById(R.id.edt_message)
        txt_number = findViewById(R.id.txt_mobile_number)
        txt_count = findViewById(R.id.txt_count)
        btn_manual = findViewById(R.id.btn_manual)
        btn_sms = findViewById(R.id.btn_sms)
        btn_whatsapp = findViewById(R.id.btn_whatsapp)
        btn_whatsapp_each_word = findViewById(R.id.btn_whatsapp2)
        btn_choose = findViewById(R.id.button_choose_contacts)
        button_choose_group = findViewById(R.id.button_choose_group)
        button_choose_template = findViewById(R.id.button_choose_template)
        btn_send = findViewById(R.id.btn_send)
        rv_contact_list = findViewById(R.id.rv_contact_list)

        db = Room.databaseBuilder(
            this@MainActivity,
            AppDatabase::class.java, "todo-list.db"
        ).build()

        setupRecyclerview()
        //selectedContactAdapter = SelectedContactAdapter(this, results)

        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_CONTACTS
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest>,
                    token: PermissionToken
                ) { /* ... */
                }
            }).check()
        if (!isAccessibilityOn(applicationContext)) {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        btn_choose?.setOnClickListener(View.OnClickListener {
            MultiContactPicker.Builder(this@MainActivity)
                .hideScrollbar(false)
                .showTrack(true)
                .searchIconColor(Color.WHITE)
                .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE)
                .handleColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                .bubbleColor(ContextCompat.getColor(this@MainActivity, R.color.colorPrimary))
                .bubbleTextColor(Color.WHITE)
                .setTitleText("Select Contacts")
                .setLoadingType(MultiContactPicker.LOAD_ASYNC)
                .limitToColumn(LimitColumn.NONE)
                .setActivityAnimations(
                    android.R.anim.fade_in, android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
                .showPickerForResult(CONTACT_PICKER_REQUEST)
        })
        btn_sms?.setOnClickListener(View.OnClickListener {
            MySMSservice.startActionSMS(
                applicationContext, edt_message?.getText().toString(),
                txt_count?.getText().toString(), results
            )
        })
        btn_manual?.setOnClickListener(View.OnClickListener {
            val sendSms = Intent(Intent.ACTION_SEND)
            sendSms.type = "text/plain"
            sendSms.putExtra(Intent.EXTRA_TEXT, edt_message?.getText().toString())
            startActivity(sendSms)
        })
        btn_whatsapp?.setOnClickListener(View.OnClickListener {
            MySMSservice.startActionWHATSAPP(
                applicationContext, edt_message?.getText().toString(),
                txt_count?.getText().toString(), results, false
            )
        })
        btn_whatsapp_each_word?.setOnClickListener(View.OnClickListener {
            MySMSservice.startActionWHATSAPP(
                applicationContext, edt_message?.getText().toString(),
                txt_count?.getText().toString(), results, true
            )
        })
        val intent = IntentFilter("my.own.broadcast")
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver, intent)
        btn_send?.setOnClickListener(View.OnClickListener {
            Toast.makeText(applicationContext, "Send Message", Toast.LENGTH_LONG).show()
            Log.e("results", "" + results.size)
        })
        button_choose_template?.setOnClickListener(View.OnClickListener { })

        button_choose_group?.setOnClickListener(View.OnClickListener {

            lifecycleScope.launch(Dispatchers.IO){
                var temp = db?.appDao()?.getAllContactGroup()
                contactGroupDataList.clear()
                contactGroupDataList.addAll(temp?:ArrayList())
            }
            dialogShowList(
                this@MainActivity,
                "Select Group",
                contactGroupDataList
            ){
                var groupContact = ArrayList<ContactResult>()
                groupContact.addAll(it.groupContact?:ArrayList())
                selectedContactAdapter?.addList(groupContact)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CONTACT_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                results = MultiContactPicker.obtainResult(data)
                val names = StringBuilder(results[0].displayName)
                for (j in results.indices) {
                    if (j != 0) names.append(", ").append(results[j].displayName)
                }
                txt_number!!.setText(names)
                Log.d("MyTag", results[0].displayName)

                selectedContactAdapter?.addList(results)
                //setupRecyclerview()
            } else if (resultCode == RESULT_CANCELED) {
                println("User closed the picker without selecting items.")
            }
        }
    }

    private fun setupRecyclerview() {
        rv_contact_list!!.layoutManager = GridLayoutManager(this, 1)
        selectedContactAdapter = SelectedContactAdapter(this, results)
        rv_contact_list!!.adapter = selectedContactAdapter
    }

    private val myLocalBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val result = intent.getStringExtra("result")
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
        }
    }

    private fun isAccessibilityOn(context: Context): Boolean {
        var accessibilityEnabled = 0
        val service =
            context.packageName + "/" + WhatAppAccessibilityService::class.java.canonicalName
        try {
            accessibilityEnabled = Settings.Secure.getInt(
                context.applicationContext.contentResolver,
                Settings.Secure.ACCESSIBILITY_ENABLED
            )
        } catch (ignored: SettingNotFoundException) {
        }
        val colonSplitter = SimpleStringSplitter(':')
        if (accessibilityEnabled == 1) {
            val settingValue = Settings.Secure.getString(
                context.applicationContext.contentResolver,
                Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
            )
            if (settingValue != null) {
                colonSplitter.setString(settingValue)
                while (colonSplitter.hasNext()) {
                    val accessibilityService = colonSplitter.next()
                    if (accessibilityService.equals(service, ignoreCase = true)) {
                        return true
                    }
                }
            }
        }
        return false
    }

    companion object {
        private const val CONTACT_PICKER_REQUEST = 202
    }
}