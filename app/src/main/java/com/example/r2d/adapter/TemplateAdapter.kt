package com.example.r2d.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.database.ContactGroupData
import com.example.r2d.database.TemplateData
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import kotlinx.android.synthetic.main.item_group.view.*
import kotlinx.android.synthetic.main.selected_contact_item.view.*


class TemplateAdapter(
    private val context: Context,
    private var arrList: ArrayList<TemplateData>,
    private var itemCB: (TemplateData) -> Unit
) :
    RecyclerView.Adapter<TemplateAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = arrList[position]

        holder.tvTitle.setText("Template ${position+1}")
        holder.tvSubTitle.setText(item.template)
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