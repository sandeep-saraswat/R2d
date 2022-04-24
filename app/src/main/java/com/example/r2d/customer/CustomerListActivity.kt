package com.example.r2d.customer

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.r2d.adapter.CustomerListAdapter
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.CustomerTable
import com.example.r2d.databinding.ActivityCustomerListBinding
import com.example.r2d.product.EditProductActivity
import com.example.r2d.utils.Const
import com.example.r2d.utils.EnumAction
import com.example.r2d.utils.Progress
import com.example.r2d.utils.confirmationDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class CustomerListActivity : AppCompatActivity() {

    lateinit var binding:ActivityCustomerListBinding

    private var db: AppDatabase? = null

    lateinit var customerList: MutableList<CustomerTable>

    var arrayListHashMapActionSpinner: ArrayList<HashMap<String, String>>? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.toolbar?.txtViewTitle.text = "Customer List"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }

        db = AppDatabase.invoke(applicationContext)

        prepareActionValue()

        binding?.txtViewAddCustomer?.setOnClickListener {
            var intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getAllCustomerFromDb()
    }

    private fun prepareActionValue() {

        arrayListHashMapActionSpinner = ArrayList()
        val actionValue = EnumAction.values()
        for (cat in actionValue) {
            val hashMap = HashMap<String, String>()
            hashMap[Const.KEY_ID] = cat.id.toString()
            hashMap[Const.KEY_NAME] = cat.actionName
            arrayListHashMapActionSpinner!!.add(hashMap)
        }

    }

    private fun getAllCustomerFromDb() {

        Progress.start(this)
        lifecycleScope.launch(Dispatchers.IO) {
            customerList = db?.appDao()?.getAllCustomer()!!
            Log.e("customerList", "" + customerList.size)
            //productList.addAll(productList)

            if (customerList.size != 0) {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding?.txtViewCustomerNotFound?.visibility = View.GONE
                    setRecycler()
                }
            } else {
                binding?.txtViewCustomerNotFound?.visibility = View.VISIBLE
            }

            withContext(Dispatchers.Main) {
                Progress.stop()
            }
        }
    }


    lateinit var customerListAdapter: CustomerListAdapter
    fun setRecycler() {
        // set list
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 1)
        customerListAdapter = CustomerListAdapter(
            this,
            customerList,
            arrayListHashMapActionSpinner
        ) { itemPosition, action ->

            when (action) {
                "1" -> {
                    Log.e("action", "edit")
                    var intent = Intent(this, EditCustomerActivity::class.java)
                    intent.putExtra("customer",customerList.get(itemPosition))
                    startActivity(intent)
                }

                "2" -> {
                    Log.e("action", "delete")

                    confirmationDialog(this, "Select", "Do you want delete customer?") {
                        if (it) {
                            Progress.start(this)
                            lifecycleScope.launch(Dispatchers.IO) {
                                db?.appDao()?.deleteCustomerTable(customerList.get(itemPosition))

                                withContext(Dispatchers.Main) {
                                    Progress.stop()
                                    customerList.removeAt(itemPosition)
                                    customerListAdapter.notifyDataSetChanged()

                                    if(customerList.size == 0){
                                        binding?.txtViewCustomerNotFound?.visibility = View.VISIBLE
                                    }
                                }
                            }

                        }
                    }

                }
            }

        }

        binding?.recyclerView?.adapter = customerListAdapter
    }
    
}