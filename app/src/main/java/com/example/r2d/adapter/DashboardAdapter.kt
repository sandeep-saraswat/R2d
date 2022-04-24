package com.example.r2d.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.utils.EnumDashboard
import kotlinx.android.synthetic.main.item_dashboard.view.*


class DashboardAdapter(
    private val context: Context,
    private var arrList: ArrayList<EnumDashboard>,
    private var itemCB: (item:EnumDashboard) -> Unit
) :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_dashboard, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var item = arrList[position]

        holder.imgView.setImageResource(item.icon)
        holder.txtViewTittle.setText(item.tittle)

        holder.itemCard.setOnClickListener { itemCB(item) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgView = itemView.imgView
        val txtViewTittle = itemView.txtViewTittle
        val itemCard = itemView.itemCard
    }

    override fun getItemCount(): Int {
        return arrList.size
    }


}