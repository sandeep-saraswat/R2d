package com.example.r2d.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.database.ContactGroupData
import com.example.r2d.database.ProductTable
import com.example.r2d.database.TemplateData
import com.example.r2d.utils.Const
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.item_product_list.view.*
import kotlinx.android.synthetic.main.selected_contact_item.view.*
import java.util.ArrayList
import java.util.HashMap


class ProductListAdapter(
    private val context: Context,
    private var arrList: MutableList<ProductTable>,
    private var actionList: ArrayList<HashMap<String, String>>?,
    private var itemCB: (position:Int,action:String) -> Unit
) :
    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product_list, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = arrList[position]

        holder.imgView.setImageBitmap(item.productImage)
        //binding.imgViewCamera.setImageBitmap(imageBitmap)
        var positionStr = position+1
        holder.txtViewSl.setText(positionStr.toString())
        holder.txtViewName.setText(item.productName)
        holder.txtViewUnit.setText(item.productUnitName)
        holder.txtViewStock.setText(item.stock)
        holder.txtViewPrice.setText(item.productPrice)

        setActionSpinner(holder.spinnerAction){
            when(it)
            {
                "1" -> {
                    // for edit
                }

                "2" -> {
                    // for delete
//                    arrList.removeAt(position)
//                    notifyDataSetChanged()
                }
            }
            itemCB(position,it)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtViewSl = itemView.txtViewSl
        val imgView = itemView.imgView
        val txtViewName = itemView.txtViewName
        val txtViewUnit = itemView.txtViewUnit
        val txtViewStock = itemView.txtViewStock
        val txtViewPrice = itemView.txtViewPrice
        val spinnerAction = itemView.spinnerAction
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun setActionSpinner(spinner: AppCompatSpinner,itemCB: (action:String) -> Unit)
    {
        var actionAdapterSpinner = AdapterSpinner(context,R.layout.layout_spinner_dropdown_view,actionList)
        spinner.adapter = actionAdapterSpinner

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var id = actionList?.get(position)?.get(Const.KEY_ID)
                var name = actionList?.get(position)?.get(Const.KEY_NAME)
                Log.e("Id",""+id)
                Log.e("Name",""+name)
                if(!id.equals("0"))
                {
                    itemCB(id!!)
                }

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }
    }


}