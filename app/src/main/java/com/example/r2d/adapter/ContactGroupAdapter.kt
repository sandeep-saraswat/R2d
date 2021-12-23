package com.example.r2d.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.database.ContactGroupData
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.selected_contact_item.view.*


class ContactGroupAdapter(
    private val context: Context,
    private var arrList: ArrayList<ContactGroupData>,
    private var itemCB: (ContactGroupData) -> Unit
) :
    RecyclerView.Adapter<ContactGroupAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = arrList[position]

        holder.tvTitle.setText(item.groupName)
        holder.tvSubTitle.setText(item.groupContact?.joinToString {
            it.displayName
        })

        holder.linLayParent.setOnClickListener {
            itemCB(item)
        }

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle = itemView.tvTitle
        val tvSubTitle = itemView.tvSubTitle
        val linLayParent = itemView.linLayParent
    }

    override fun getItemCount(): Int {
        return arrList.size
    }


}