package com.example.r2d.template

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import com.example.r2d.R
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.TemplateData
import com.example.r2d.utils.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddTemplateActivity : AppCompatActivity(), View.OnClickListener {

    private var imgViewBack: ImageView? = null
    private var txtViewTitle: TextView? = null
    private var btnSaveTemplate: Button? = null
    private var edtTemplate: EditText? = null

    private var db: AppDatabase?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_template)

        imgViewBack = findViewById(R.id.imgViewBack) as ImageView?
        txtViewTitle = findViewById(R.id.txtViewTitle) as TextView?
        btnSaveTemplate = findViewById(R.id.btn_save_template) as Button?
        edtTemplate = findViewById(R.id.edt_template) as EditText?

        txtViewTitle?.text = "Add Template"

        db = Room.databaseBuilder(
            this@AddTemplateActivity,
            AppDatabase::class.java, "todo-list.db"
        ).build()


        /*lifecycleScope.launch(Dispatchers.IO){
            var tempList = db?.appDao()?.getAllTemplate()
            Log.e("templist",""+tempList?.size)
        }*/

        imgViewBack?.setOnClickListener(this)
        btnSaveTemplate?.setOnClickListener(this)

    }

    override fun onClick(v: View?) {

        when(v){

            btnSaveTemplate -> {

                var template = edtTemplate?.text.toString()
                if(template.isNotEmpty())
                {
                    var templateModel = TemplateData(0, template)

                    lifecycleScope.launch(Dispatchers.IO) {
                        withContext(Dispatchers.Main){
                            Progress.start(this@AddTemplateActivity)
                        }
                        var row = db?.appDao()?.insertTemplate(templateModel)
                        Log.e("inserted row",""+row)
                        withContext(Dispatchers.Main){
                            Progress.stop()
                            edtTemplate?.setText("")
                            Toast.makeText(this@AddTemplateActivity,"Template add successfully.",Toast.LENGTH_LONG).show()
                        }
                    }

                }else{
                    Toast.makeText(this, "Please enter template.",Toast.LENGTH_LONG).show()
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