package com.example.r2d.product

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.r2d.R
import com.example.r2d.adapter.AdapterSpinner
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ProductTable
import com.example.r2d.databinding.ActivityAdditemBinding
import com.example.r2d.utils.*
import kotlinx.coroutines.*
import java.util.ArrayList
import java.util.HashMap
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

import android.graphics.BitmapFactory

import android.graphics.Bitmap

import android.content.ClipData

import android.content.Intent

import android.os.Build

import androidx.annotation.RequiresApi

import androidx.core.app.ActivityCompat

import android.content.pm.PackageManager
import android.provider.MediaStore

class AdditemActivity : AppCompatActivity() {
    /*private var itemname: EditText? = null
    private var itemcategory: EditText? = null
    private var itemprice: EditText? = null
    private var quantity:EditText? = null

    var additemtodatabase: Button? = null
    */

    lateinit var binding:ActivityAdditemBinding
    private var db:AppDatabase?=null

    lateinit var productTable:ProductTable

    var arrayListHashMapCategorySpinner: ArrayList<HashMap<String, String>>? = null
    var categoryAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapBrandSpinner: ArrayList<HashMap<String, String>>? = null
    var brandAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapProUnitSpinner: ArrayList<HashMap<String, String>>? = null
    var proUnitAdapterSpinner: AdapterSpinner? = null
    var arrayListHashMapProTaxSpinner: ArrayList<HashMap<String, String>>? = null
    var proTaxAdapterSpinner: AdapterSpinner? = null

    var isFromList:Boolean = false
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdditemBinding.inflate(layoutInflater)
        //setContentView(R.layout.activity_additem)
        setContentView(binding.root)

        isFromList = intent.getBooleanExtra("isFromList",false)
        productTable = ProductTable(0)

        binding?.toolbar?.txtViewTitle.text = "Add Product"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }

        db = AppDatabase.invoke(applicationContext)

        /*resulttextview = findViewById(R.id.barcodeview)
        additemtodatabase = findViewById(R.id.additembuttontodatabase)
        itemname = findViewById(R.id.edititemname)
        itemcategory = findViewById(R.id.editcategory)
        itemprice = findViewById(R.id.editprice)
        quantity=  findViewById(R.id.quantity)
          db = Room.databaseBuilder(
            this@AdditemActivity,
            AppDatabase::class.java, "todo-list.db"
        ).build()
        additemtodatabase!!.setOnClickListener { additem() }*/

        binding?.txtViewSubmit?.setOnClickListener {

            var pro = getProduct()
            if(pro != null) {
                Progress.start(this)
                lifecycleScope.launch(Dispatchers.IO) {
                    db?.appDao()?.insertProduct(pro)

                    withContext(Dispatchers.Main){
                        Progress.stop()
                        Toast.makeText(this@AdditemActivity,"Product save successfully.",Toast.LENGTH_LONG).show()
                        if(isFromList){
                            finish()
                        }else {
                            resetDataAfterSave()
                        }
                    }
                }
            }else{
                Toast.makeText(this,"Please fill details.",Toast.LENGTH_LONG).show()
            }
        }

        setupSpinner()

        binding?.relLayCamera?.setOnClickListener {
          //  loadImagesFromGallery()
            dispatchTakePictureIntent()
        }
    }

    private fun setupSpinner() {

        arrayListHashMapCategorySpinner = ArrayList()
        arrayListHashMapBrandSpinner = ArrayList()
        arrayListHashMapProUnitSpinner = ArrayList()
        arrayListHashMapProTaxSpinner = ArrayList()

        // region category
        val categoryValue = EnumCategory.values()
        for(cat in categoryValue){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = cat.id.toString()
            hashMap1[Const.KEY_NAME] = cat.categoryName
            arrayListHashMapCategorySpinner!!.add(hashMap1)
        }

        categoryAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapCategorySpinner)
        binding?.spinnerCategory?.adapter = categoryAdapterSpinner

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
                productTable.categoryId = id.toString()
                productTable.categoryName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // brand region
        val brandValue = EnumBrand.values()
        for(item in brandValue){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = item.id.toString()
            hashMap1[Const.KEY_NAME] = item.brandName
            arrayListHashMapBrandSpinner!!.add(hashMap1)
        }

        brandAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapBrandSpinner)
        binding?.spinnerBrand?.adapter = brandAdapterSpinner

        binding?.spinnerBrand?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapBrandSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapBrandSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("catId",""+id)
                Log.e("catName",""+name)
                productTable.brandId = id.toString()
                productTable.brandName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // product unit region
        val productUnitValue = EnumProductUnit.values()
        for(item in productUnitValue){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = item.id.toString()
            hashMap1[Const.KEY_NAME] = item.productUnitName
            arrayListHashMapProUnitSpinner!!.add(hashMap1)
        }

        proUnitAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapProUnitSpinner)
        binding?.spinnerProductUnit?.adapter = proUnitAdapterSpinner

        binding?.spinnerProductUnit?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapProUnitSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapProUnitSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("catId",""+id)
                Log.e("catName",""+name)
                productTable.productUnitId = id.toString()
                productTable.productUnitName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

        // product tax region
        val productTaxValue = EnumProductTax.values()
        for(item in productTaxValue){
            val hashMap1 = HashMap<String, String>()
            hashMap1[Const.KEY_ID] = item.id.toString()
            hashMap1[Const.KEY_NAME] = item.productTaxName
            arrayListHashMapProTaxSpinner!!.add(hashMap1)
        }

        proTaxAdapterSpinner = AdapterSpinner(this,
            R.layout.layout_spinner_dropdown_view,arrayListHashMapProTaxSpinner)
        binding?.spinnerProductTax?.adapter = proTaxAdapterSpinner

        binding?.spinnerProductTax?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = arrayListHashMapProTaxSpinner?.get(position)?.get(Const.KEY_ID)
                var name = arrayListHashMapProTaxSpinner?.get(position)?.get(Const.KEY_NAME)
                Log.e("catId",""+id)
                Log.e("catName",""+name)
                productTable.productTaxId = id.toString()
                productTable.productTaxName = name.toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
        // end region

    }

    fun getProduct():ProductTable?{

        if(isValidProduct()) {
            productTable.productName = binding?.editTextProductName.text.toString().trim()
            productTable.productCode = binding?.editTextProductCode.text.toString().trim()
            productTable.productPrice = binding?.edtTextProductPrice.text.toString().trim()
            productTable.discount = binding?.edtTextDiscount.text.toString().trim()
            productTable.stock = binding?.edtTextStock.text.toString().trim()

            return productTable
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

    fun resetDataAfterSave()
    {
        binding?.editTextProductName.setText("")
        binding?.editTextProductCode.setText("")
        binding?.edtTextProductPrice.setText("")
        binding?.edtTextDiscount.setText("")
        binding?.edtTextStock.setText("")
        binding.spinnerCategory.setSelection(0)
        binding.spinnerBrand.setSelection(0)
        binding.spinnerProductTax.setSelection(0)
        binding.spinnerProductUnit.setSelection(0)
    }
    // addding item to databse
   /* private fun additem() {

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
            GlobalScope.launch(Dispatchers.IO) {
                db?.appDao()?.insertAll(itemsData)
                val  data = db?.appDao()?.getAllData()

                withContext(Dispatchers.Main){
                Toast.makeText(this@AdditemActivity, "$itemnameValue Added", Toast.LENGTH_SHORT).show()
                }
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
    }*/

    // logout below
    /*private fun Logout() {
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
    }*/

    private fun loadImagesFromGallery() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
            return
        }
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

   /*
    protected fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            imageFragmentContainer.setVisibility(View.VISIBLE)
            bitmaps = ArrayList<Any>()
            imageSources = ArrayList<Any>()
            val clipData = data.clipData
            //clip data will be null if user select one item from gallery
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val imageUri: Uri = clipData.getItemAt(i).uri
                    try {
                        val `is`: InputStream? = contentResolver.openInputStream(imageUri)
                        val bitmap = BitmapFactory.decodeStream(`is`)
                        bitmaps.add(bitmap)
                        val imageSource: String = ImageBitmapString.BitMapToString(bitmap)
                        imageSources.add(imageSource)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            } else {
                val imageUri: Uri? = data.data
                try {
                    val `is`: InputStream? = contentResolver.openInputStream(imageUri)
                    val bitmap = BitmapFactory.decodeStream(`is`)
                    bitmaps.add(bitmap)
                    val imageSource: String = ImageBitmapString.BitMapToString(bitmap)
                    imageSources.add(imageSource)
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                }
            }
            val imageRecycleView = findViewById<RecyclerView>(R.id.imageRecycleView)
            imageRecycleView.setHasFixedSize(true)
            imageRecycleView.layoutManager =
                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(bitmaps)
            imageRecycleView.adapter = adapter
        }
    }*/

    val REQUEST_IMAGE_CAPTURE = 1
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            // display error state to the user
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            binding.imgViewCamera.setImageBitmap(imageBitmap)
            productTable.productImage = imageBitmap
        }
    }
}
