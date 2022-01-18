package com.example.r2d.autosmssender

import android.Manifest
import android.content.*
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.provider.Settings.SettingNotFoundException
import android.text.TextUtils.SimpleStringSplitter
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.r2d.R
import com.example.r2d.adapter.SelectedContactAdapter
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ContactGroupData
import com.example.r2d.database.TemplateData
import com.example.r2d.utils.AlertDialogHelper.Companion.dialogShowList
import com.example.r2d.utils.AlertDialogHelper.Companion.dialogShowTemplateList
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import com.example.r2d.wafflecopter.multicontactpicker.LimitColumn
import com.example.r2d.wafflecopter.multicontactpicker.MultiContactPicker
import com.example.r2d.wafflecopter.multicontactpicker.RxContacts.Contact
import com.example.r2d.wafflecopter.multicontactpicker.RxContacts.PhoneNumber

import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class MainActivity : AppCompatActivity() {
    private var imgViewBack: ImageView? = null

    private var edt_message: EditText? = null
    private var txt_number: EditText? = null
    private var txt_count: EditText? = null
    private var btn_manual: Button? = null
    private var btn_sms: Button? = null
    private var btn_whatsapp: Button? = null
    private var btn_choose: Button? = null
    private var btn_attachment: Button? = null
    private var button_choose_group: Button? = null
    private var button_choose_template: Button? = null
    private var btn_send: Button? = null
    private var rv_contact_list: RecyclerView? = null

    private var edt_no: EditText? = null
    private var btn_add_mobile_number: Button? = null

    var results = ArrayList<ContactResult>()
    var selectedContactAdapter: SelectedContactAdapter? = null
    private var btn_whatsapp_each_word: Button? = null
    private var file_path: String? = null

    private var db: AppDatabase? = null
    private  var from:String?=null

    var contactGroupDataList= ArrayList<ContactGroupData>()
    var templateDataList= ArrayList<TemplateData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_massaging)
        imgViewBack = findViewById(R.id.imgViewBack)

        edt_message = findViewById(R.id.edt_message)
        txt_number = findViewById(R.id.txt_mobile_number)
        txt_count = findViewById(R.id.txt_count)
        btn_manual = findViewById(R.id.btn_manual)
        btn_sms = findViewById(R.id.btn_sms)
        btn_whatsapp = findViewById(R.id.btn_whatsapp)
        btn_whatsapp_each_word = findViewById(R.id.btn_whatsapp2)
        btn_attachment = findViewById(R.id.btn_attachment)
        btn_choose = findViewById(R.id.button_choose_contacts)
        button_choose_group = findViewById(R.id.button_choose_group)
        button_choose_template = findViewById(R.id.button_choose_template)
        btn_send = findViewById(R.id.btn_send)
        rv_contact_list = findViewById(R.id.rv_contact_list)

        edt_no = findViewById(R.id.edt_no)
        btn_add_mobile_number = findViewById(R.id.btn_add_mobile_number)

        from = intent?.getStringExtra("from")

        if(from != null && from.equals("sms"))
        {
            btn_attachment?.visibility = View.GONE
        }else{
            btn_attachment?.visibility = View.VISIBLE
        }

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
                txt_count?.getText().toString(), results, false,file_path
            )
        })
        btn_whatsapp_each_word?.setOnClickListener(View.OnClickListener {
            MySMSservice.startActionWHATSAPP(
                applicationContext, edt_message?.getText().toString(),
                txt_count?.getText().toString(), results, true,file_path
            )
        })
        btn_attachment?.setOnClickListener(View.OnClickListener { v ->

            intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*"
            startActivityForResult(intent, 7)
        })
        val intent = IntentFilter("my.own.broadcast")
        LocalBroadcastManager.getInstance(this).registerReceiver(myLocalBroadcastReceiver, intent)
        btn_send?.setOnClickListener(View.OnClickListener {
            Log.e("results", "" + results.size)
            if(from.equals("sms")){
//                val sendSms = Intent(Intent.ACTION_SEND)
//                sendSms.type = "text/plain"
//                sendSms.putExtra(Intent.EXTRA_TEXT, edt_message?.getText().toString())
//                startActivity(sendSms)

                MySMSservice.startActionSMS(
                    applicationContext, edt_message?.getText().toString(),
                    "1", results
                )

            }else {
                MySMSservice.startActionWHATSAPP(
                    applicationContext, edt_message?.getText().toString(),
                    "1", results, false,file_path
                )
            }
        })

        button_choose_template?.setOnClickListener(View.OnClickListener {


            lifecycleScope.launch(Dispatchers.IO){
                var temp = db?.appDao()?.getAllTemplate()
                templateDataList.clear()
                templateDataList.addAll(temp?:ArrayList())

                withContext(Dispatchers.Main){

                    if(templateDataList.size == 0){
                        Toast.makeText(this@MainActivity,"Template not available.",Toast.LENGTH_LONG).show()
                    }else {
                        dialogShowTemplateList(
                            this@MainActivity,
                            "Select Template",
                            templateDataList
                        ) {
                            edt_message?.setText(it.template)
                        }
                    }

                }
            }

        })

        button_choose_group?.setOnClickListener(View.OnClickListener {

            lifecycleScope.launch(Dispatchers.IO){
                var temp = db?.appDao()?.getAllContactGroup()
                contactGroupDataList.clear()
                contactGroupDataList.addAll(temp?:ArrayList())

                withContext(Dispatchers.Main){

                    if(contactGroupDataList.size == 0){
                        Toast.makeText(this@MainActivity,"Group not available.",Toast.LENGTH_LONG).show()
                    }else {
                        dialogShowList(
                            this@MainActivity,
                            "Select Group",
                            contactGroupDataList
                        ) {
                            var groupContact = ArrayList<ContactResult>()
                            groupContact.addAll(it.groupContact ?: ArrayList())
                            selectedContactAdapter?.addList(groupContact)
                        }
                    }

                }
            }

        })

        imgViewBack?.setOnClickListener {
            finish()
        }

        btn_add_mobile_number?.setOnClickListener {
            addContact()
        }
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
        when (requestCode) {
            7 -> if (resultCode == RESULT_OK) {
               val  currImageURI = data?.data;
                file_path = data?.data?.path

            }
        }
    }
    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = uri?.let { contentResolver.query(it, null, null, null, null) }
        cursor?.moveToFirst()
        val idx: Int? = cursor?.getColumnIndex(MediaStore.Images.ImageColumns.DATA)

        return idx?.let { cursor?.getString(it) }
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

    fun addContact()
    {
        var num = edt_no?.text.toString().trim()
        if(num.isEmpty())
        {
            Toast.makeText(this,"Please Enter Number.",Toast.LENGTH_LONG).show()
            return
        }

        if(num.length < 10)
        {
            Toast.makeText(this,"Please Enter Valid Number.",Toast.LENGTH_LONG).show()
            return
        }
        var id = System.currentTimeMillis()
        var contact = Contact(id,num)
        var ph = PhoneNumber(num,num)
        var phList = mutableListOf<PhoneNumber>()
        phList.add(ph)
        contact.phoneNumbers = phList
        var contactResult = ContactResult(contact)

        selectedContactAdapter?.addContact(contactResult)
    }


}