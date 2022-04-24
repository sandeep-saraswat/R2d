package com.example.r2d.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.r2d.R
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.CustomerTable
import com.example.r2d.database.ProductTable
import com.example.r2d.databinding.ActivityAddCustomerBinding
import com.example.r2d.databinding.ActivityAdditemBinding
import com.example.r2d.utils.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCustomerActivity : AppCompatActivity() {

    lateinit var binding : ActivityAddCustomerBinding
    private var db:AppDatabase?=null
    lateinit var customerTable:CustomerTable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.toolbar?.txtViewTitle.text = "Add Customer"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }
        customerTable = CustomerTable(0)

        db = AppDatabase.invoke(applicationContext)

        binding?.txtViewSubmit?.setOnClickListener {

            var customer = getCustomer()
            if(customer != null) {
                Progress.start(this)
                lifecycleScope.launch(Dispatchers.IO) {
                    db?.appDao()?.insertCustomerTable(customer)

                    withContext(Dispatchers.Main){
                        Progress.stop()
                        Toast.makeText(this@AddCustomerActivity,"Customer save successfully.", Toast.LENGTH_LONG).show()
                        finish()

                    }
                }
            }else{
                Toast.makeText(this,"Please fill details.", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun getCustomer(): CustomerTable?{

        if(isValidCustomer()) {
            customerTable.customerName = binding?.editTextCustomerName.text.toString().trim()
            customerTable.phone = binding?.editTextPhone.text.toString().trim()
            customerTable.city = binding?.editTextCity.text.toString().trim()
            customerTable.emailAddress = binding?.editTextEmailAddress.text.toString().trim()
            customerTable.zipcode = binding?.editTextZipCode.text.toString().trim()

            return customerTable
        }else{
            return null
        }

    }

    fun isValidCustomer():Boolean{
        if(binding?.editTextCustomerName.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.editTextPhone.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.editTextCity.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.editTextEmailAddress.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.editTextZipCode.text.toString().trim().isEmpty()){
            return false
        }
        return true
    }
}