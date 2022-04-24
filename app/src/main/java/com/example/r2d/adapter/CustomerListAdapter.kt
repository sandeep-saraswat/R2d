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
import com.example.r2d.database.CustomerTable
import com.example.r2d.utils.Const
import kotlinx.android.synthetic.main.item_customer_list.view.*
import kotlinx.android.synthetic.main.item_product_list.view.spinnerAction
import java.util.*


class CustomerListAdapter(
    private val context: Context,
    private var arrList: MutableList<CustomerTable>,
    private var actionList: ArrayList<HashMap<String, String>>?,
    private var itemCB: (position:Int,action:String) -> Unit
) :
    RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_customer_list, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = arrList[position]

        holder.txtViewCustomerName.setText(item.customerName)
        holder.txtViewPhone.setText(item.phone)
        holder.txtViewCity.setText(item.city)
        holder.txtViewEmailAddress.setText(item.emailAddress)
        holder.txtViewZipCode.setText(item.zipcode)

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
        val txtViewCustomerName = itemView.txtViewCustomerName
        val txtViewPhone = itemView.txtViewPhone
        val txtViewCity = itemView.txtViewCity
        val txtViewEmailAddress = itemView.txtViewEmailAddress
        val txtViewZipCode = itemView.txtViewZipCode
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