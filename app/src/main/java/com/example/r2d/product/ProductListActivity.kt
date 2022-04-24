package com.example.r2d.product

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.r2d.adapter.ProductListAdapter
import com.example.r2d.database.AppDatabase
import com.example.r2d.database.ProductTable
import com.example.r2d.databinding.ActivityProductListBinding
import com.example.r2d.utils.Const
import com.example.r2d.utils.EnumAction
import com.example.r2d.utils.Progress
import com.example.r2d.utils.confirmationDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ProductListActivity : AppCompatActivity() {

    lateinit var binding: ActivityProductListBinding
    private var db: AppDatabase? = null

    lateinit var productList: MutableList<ProductTable>

    var arrayListHashMapActionSpinner: ArrayList<HashMap<String, String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding?.toolbar?.txtViewTitle.text = "Product List"
        binding?.toolbar?.imgViewBack.setOnClickListener { finish() }

        db = AppDatabase.invoke(applicationContext)

        prepareActionValue()

        //getAllProductFromDb()

        binding?.txtViewAddProduct?.setOnClickListener {
            var intent = Intent(this, AdditemActivity::class.java)
            intent.putExtra("isFromList", true)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        getAllProductFromDb()
    }

    private fun getAllProductFromDb() {

        Progress.start(this)
        lifecycleScope.launch(Dispatchers.IO) {
            productList = db?.appDao()?.getAllProduct()!!
            Log.e("proList", "" + productList.size)
            //productList.addAll(productList)

            if (productList.size != 0) {
                lifecycleScope.launch(Dispatchers.Main) {
                    binding?.txtViewProductNotFound?.visibility = View.GONE
                    setRecycler()
                }
            } else {
                binding?.txtViewProductNotFound?.visibility = View.VISIBLE
            }

            withContext(Dispatchers.Main) {
                Progress.stop()
            }
        }
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

    lateinit var productListAdapter:ProductListAdapter
    fun setRecycler() {
        // set list
        binding?.recyclerView?.layoutManager = GridLayoutManager(this, 1)
        productListAdapter = ProductListAdapter(
            this,
            productList,
            arrayListHashMapActionSpinner
        ) { itemPosition, action ->

            when (action) {
                "1" -> {
                    Log.e("action", "edit")
                    var intent = Intent(this,EditProductActivity::class.java)
                    intent.putExtra("product",productList.get(itemPosition))
                    startActivity(intent)
                }

                "2" -> {
                    Log.e("action", "delete")

                    confirmationDialog(this, "Select", "Do you want delete product?") {
                        if (it) {
                            Progress.start(this)
                            lifecycleScope.launch(Dispatchers.IO) {
                                db?.appDao()?.deleteProduct(productList.get(itemPosition))

                                withContext(Dispatchers.Main) {
                                    Progress.stop()
                                    productList.removeAt(itemPosition)
                                    productListAdapter.notifyDataSetChanged()

                                    if(productList.size == 0){
                                        binding?.txtViewProductNotFound?.visibility = View.VISIBLE
                                    }

                                }
                            }

                        }
                    }

                }
            }

        }

        binding?.recyclerView?.adapter = productListAdapter
    }
}