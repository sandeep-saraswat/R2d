package com.example.r2d.group

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.r2d.R
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ContactGroupData
import com.example.r2d.utils.Progress
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import com.example.r2d.wafflecopter.multicontactpicker.LimitColumn
import com.example.r2d.wafflecopter.multicontactpicker.MultiContactPicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*


class CreateGroup : AppCompatActivity(), View.OnClickListener {

    private val CONTACT_PICKER_REQUEST = 202
    var results: List<ContactResult> = ArrayList()
    private var db: AppDatabase? = null

    private var imgViewBack: ImageView? = null
    private var txtViewTitle: TextView? = null
    private var groupName_et: EditText? = null
    private var contact_et: EditText? = null
    private var if_proceed_btn: Button? = null
    private var button_choose_contacts: Button? = null
    private var rvContactList: RecyclerView? = null

    private var groupName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_group)

        imgViewBack = findViewById(R.id.imgViewBack) as ImageView?
        txtViewTitle = findViewById(R.id.txtViewTitle) as TextView?
        groupName_et = findViewById(R.id.groupName_et) as EditText?
        contact_et = findViewById(R.id.contact_et) as EditText?
        if_proceed_btn = findViewById(R.id.if_proceed_btn) as Button?
        button_choose_contacts = findViewById(R.id.button_choose_contacts) as Button?
        rvContactList = findViewById(R.id.rv_contact_list) as RecyclerView?

        txtViewTitle?.text = "Create Group"

        db = Room.databaseBuilder(
            this@CreateGroup,
            AppDatabase::class.java, "todo-list.db"
        ).build()

        imgViewBack?.setOnClickListener(this)
        if_proceed_btn?.setOnClickListener(this)
        button_choose_contacts?.setOnClickListener(this)

        // temp to check db
        lifecycleScope.launch(Dispatchers.IO){
            var tempList = db?.appDao()?.getAllContactGroup()
            Log.e("templist",""+tempList?.size)
        }
    }

    override fun onClick(v: View?) {

        when (v) {
            if_proceed_btn -> {
                groupName = groupName_et?.text.toString().trim()

                if (groupName.length == 0) {
                    Toast.makeText(this, "Please enter group name.", Toast.LENGTH_LONG).show()
                    return
                } else if (results.size == 0) {
                    Toast.makeText(this, "Please select contact.", Toast.LENGTH_LONG).show()
                    return
                } else {
                    // save group
                    lifecycleScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main) {
                            Progress.start(this@CreateGroup)
                        }
                        var contactGroupData = ContactGroupData(0, groupName, results)
                        var row = db?.appDao()?.insertContactGroup(contactGroupData)
                        Log.e("inserted row", "" + row)
                        withContext(Dispatchers.Main) {
                            Progress.stop()
                            groupName_et?.setText("")
                            results = ArrayList()
                            contactAdapter?.updateList(results)

                            Toast.makeText(
                                this@CreateGroup,
                                "Group save successfully.",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

            }

            imgViewBack -> {
                finish()
            }

            button_choose_contacts -> {
                MultiContactPicker.Builder(this@CreateGroup)
                    .hideScrollbar(false)
                    .showTrack(true)
                    .searchIconColor(Color.WHITE)
                    .setChoiceMode(MultiContactPicker.CHOICE_MODE_MULTIPLE)
                    .handleColor(ContextCompat.getColor(this@CreateGroup, R.color.colorPrimary))
                    .bubbleColor(ContextCompat.getColor(this@CreateGroup, R.color.colorPrimary))
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
            }

            else -> {
                Log.e("nothing", "seleted")
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CONTACT_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                results = MultiContactPicker.obtainResult(data)
                //val names: StringBuilder = StringBuilder(results.get(0).getDisplayName())

                var names = results.joinToString {
                    it.displayName
                }
//                for (j in results.indices) {
//                    if (j != 0) names.append(", ").append(results.get(j).getDisplayName())
//                }

                Log.d("MyTag", results.get(0).getDisplayName())
                Log.d("MyTag", names)

                setupRecyclerview()

            } else if (resultCode == RESULT_CANCELED) {
                println("User closed the picker without selecting items.")
            }
        }
    }

    var contactAdapter: ContactAdapter? = null

    private fun setupRecyclerview() {
        rvContactList?.layoutManager = GridLayoutManager(this@CreateGroup, 1)
        contactAdapter = ContactAdapter(this@CreateGroup, results)
        rvContactList?.adapter = contactAdapter

    }

}