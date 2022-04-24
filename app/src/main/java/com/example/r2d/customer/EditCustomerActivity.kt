package com.example.r2d.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.r2d.R
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.CustomerTable
import com.example.r2d.databinding.ActivityAddCustomerBinding
import com.example.r2d.utils.Progress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditCustomerActivity : AppCompatActivity() {

    lateinit var binding:ActivityAddCustomerBinding
    lateinit var customer:CustomerTable
    private var db:AppDatabase?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.toolbar?.txtViewTitle.text = "Edit Customer"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }

        customer = intent.getSerializableExtra("customer") as CustomerTable

        setCustomerData()
        db = AppDatabase.invoke(applicationContext)

        binding?.txtViewSubmit?.setOnClickListener {

            var customer = getValidCustomer()
            if(customer != null) {
                Progress.start(this)
                lifecycleScope.launch(Dispatchers.IO) {
                    db?.appDao()?.insertCustomerTable(customer!!)

                    withContext(Dispatchers.Main){
                        Progress.stop()
                        Toast.makeText(this@EditCustomerActivity,"Customer edit successfully.", Toast.LENGTH_LONG).show()
                        finish()

                    }
                }
            }else{
                Toast.makeText(this,"Please fill details.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setCustomerData() {
        binding?.editTextCustomerName.setText(customer.customerName)
        binding?.editTextPhone.setText(customer.phone)
        binding?.editTextCity.setText(customer.city)
        binding?.editTextEmailAddress.setText(customer.emailAddress)
        binding?.editTextZipCode.setText(customer.zipcode)
    }

    fun getValidCustomer(): CustomerTable?{

        if(isValidCustomer()) {
            customer.customerName = binding?.editTextCustomerName.text.toString().trim()
            customer.phone = binding?.editTextPhone.text.toString().trim()
            customer.city = binding?.editTextCity.text.toString().trim()
            customer.emailAddress = binding?.editTextEmailAddress.text.toString().trim()
            customer.zipcode = binding?.editTextZipCode.text.toString().trim()

            return customer
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