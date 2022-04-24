package com.example.r2d.product

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.r2d.R
import com.example.r2d.adapter.AdapterSpinner
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ProductTable
import com.example.r2d.databinding.ActivityAdditemBinding
import com.example.r2d.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList
import java.util.HashMap

class EditProductActivity : AppCompatActivity() {

    lateinit var product:ProductTable
    lateinit var binding:ActivityAdditemBinding
    private var db:AppDatabase?=null

    var arrayListHashMapCategorySpinner: ArrayList<HashMap<String, String>>? = null
    var categoryAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapBrandSpinner: ArrayList<HashMap<String, String>>? = null
    var brandAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapProUnitSpinner: ArrayList<HashMap<String, String>>? = null
    var proUnitAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapProTaxSpinner: ArrayList<HashMap<String, String>>? = null
    var proTaxAdapterSpinner: AdapterSpinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_edit_product)
        binding = ActivityAdditemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        product = intent.getSerializableExtra("product") as ProductTable

        binding?.toolbar?.txtViewTitle.text = "Edit Product"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }

        db = AppDatabase.invoke(applicationContext)

        setProductData()

        binding?.txtViewSubmit?.setOnClickListener {

            var pro = getValidProduct()
            if(pro != null) {
                Progress.start(this)
                lifecycleScope.launch(Dispatchers.IO) {
                    db?.appDao()?.insertProduct(pro)

                    withContext(Dispatchers.Main){
                        Progress.stop()
                        Toast.makeText(this@EditProductActivity,"Product edit successfully.", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }else{
                Toast.makeText(this,"Please fill details.", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setProductData() {

        binding?.editTextProductName.setText(product.productName)
        binding?.editTextProductCode.setText(product.productCode)
        binding?.edtTextProductPrice.setText(product.productPrice)
        binding?.edtTextDiscount.setText(product.discount)
        binding?.edtTextStock.setText(product.stock)

        setupSpinner()
    }

    private fun setupSpinner() {

        arrayListHashMapCategorySpinner = ArrayList()
        arrayListHashMapBrandSpinner = ArrayList()
        arrayListHashMapProUnitSpinner = ArrayList()
        arrayListHashMapProTaxSpinner = ArrayList()

        // region category
        val categoryValue = EnumCategory.values()
        var indexForSelection = 0
        for(index in categoryValue.indices){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = categoryValue[index].id.toString()
            hashMap1[Const.KEY_NAME] = categoryValue[index].categoryName
            if(product.categoryId.equals(categoryValue[index].id.toString()))
            {
                indexForSelection = index
            }
            arrayListHashMapCategorySpinner!!.add(hashMap1)
        }
        Log.e("indexForSelection",""+indexForSelection)

        categoryAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapCategorySpinner)
        binding?.spinnerCategory?.adapter = categoryAdapterSpinner

        binding?.spinnerCategory.setSelection(indexForSelection)

        binding?.spinnerCategory?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapCategorySpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapCategorySpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("catId",""+id)
                Log.e("catName",""+name)
                product.categoryId = id.toString()
                product.categoryName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // brand region
        val brandValue = EnumBrand.values()
        var brandIndexForSelection = 0
        for(index in brandValue.indices){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = brandValue[index].id.toString()
            hashMap1[Const.KEY_NAME] = brandValue[index].brandName

            if(product.brandId.equals(brandValue[index].id.toString()))
            {
                brandIndexForSelection = index
            }
            arrayListHashMapBrandSpinner!!.add(hashMap1)
        }
        Log.e("brandIndexForSelection",""+brandIndexForSelection)

        brandAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapBrandSpinner)
        binding?.spinnerBrand?.adapter = brandAdapterSpinner

        binding?.spinnerBrand?.setSelection(brandIndexForSelection)


        binding?.spinnerBrand?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapBrandSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapBrandSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("brandId",""+id)
                Log.e("brandName",""+name)
                product.brandId = id.toString()
                product.brandName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // product unit region
        val productUnitValue = EnumProductUnit.values()
        var productUnitIndexForSelection = 0
        for(index in productUnitValue.indices){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = productUnitValue[index].id.toString()
            hashMap1[Const.KEY_NAME] = productUnitValue[index].productUnitName

            if(product.productUnitId.equals(productUnitValue[index].id.toString())){
                productUnitIndexForSelection = index
            }
            arrayListHashMapProUnitSpinner!!.add(hashMap1)
        }
        Log.e("prodUnitIndForSelec",""+productUnitIndexForSelection)


        proUnitAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapProUnitSpinner)
        binding?.spinnerProductUnit?.adapter = proUnitAdapterSpinner

        binding?.spinnerProductUnit?.setSelection(productUnitIndexForSelection)

        binding?.spinnerProductUnit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapProUnitSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapProUnitSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("productUnitId",""+id)
                Log.e("productUnitName",""+name)
                product.productUnitId = id.toString()
                product.productUnitName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // product tax region
        val productTaxValue = EnumProductTax.values()
        var productTaxIndexForSelection = 0
        for(index in productTaxValue.indices){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = productTaxValue[index].id.toString()
            hashMap1[Const.KEY_NAME] = productTaxValue[index].productTaxName

            if(product.productTaxId.equals(productTaxValue[index].id.toString())){
                productTaxIndexForSelection = index
            }
            arrayListHashMapProTaxSpinner!!.add(hashMap1)
        }
        Log.e("prodtTaxInxForSelec",""+productTaxIndexForSelection)

        proTaxAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapProTaxSpinner)
        binding?.spinnerProductTax?.adapter = proTaxAdapterSpinner

        binding?.spinnerProductTax?.setSelection(productTaxIndexForSelection)

        binding?.spinnerProductTax?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapProTaxSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapProTaxSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("productTaxId",""+id)
                Log.e("productTaxName",""+name)
                product.productTaxId = id.toString()
                product.productTaxName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

    }

    fun getValidProduct():ProductTable?{

        if(isValidProduct()) {
            product.productName = binding?.editTextProductName.text.toString().trim()
            product.productCode = binding?.editTextProductCode.text.toString().trim()
            product.productPrice = binding?.edtTextProductPrice.text.toString().trim()
            product.discount = binding?.edtTextDiscount.text.toString().trim()
            product.stock = binding?.edtTextStock.text.toString().trim()

            return product
        }else{
            return null
        }

    }

    fun isValidProduct():Boolean{
        if(binding?.editTextProductName.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.editTextProductCode.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.edtTextProductPrice.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.edtTextDiscount.text.toString().trim().isEmpty()){
            return false
        }else if(binding?.edtTextStock.text.toString().trim().isEmpty()){
            return false
        }
        return true
    }
}