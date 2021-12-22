package com.example.r2d.group

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.r2d.R
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import kotlinx.android.synthetic.main.list_row_contact_pick_item.view.*


class ContactAdapter(
    private val context: Context,
    private var arrList: List<ContactResult>
) :
    RecyclerView.Adapter<ContactAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflate the product item view
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_row_contact_pick_item, parent, false)
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var contactItem = arrList[position]

        holder.tvContactName.setText(contactItem.getDisplayName())
        holder.vRoundLetterView.setTitleText(
            contactItem.getDisplayName().get(0).toString()
        )
       // holder.vRoundLetterView.setBackgroundColor(contactItem.getBackgroundColor())

        if (contactItem.phoneNumbers.size > 0) {
            val phoneNumber = contactItem.phoneNumbers[0].number.replace("\\s+".toRegex(), "")
            val displayName = contactItem.displayName.replace("\\s+".toRegex(), "")
            if (phoneNumber != displayName) {
                holder.tvNumber.setVisibility(View.VISIBLE)
                holder.tvNumber.setText(phoneNumber)
            } else {
                holder.tvNumber.setVisibility(View.GONE)
            }
        } else {
            if (contactItem.emails.size > 0) {
                val email = contactItem.emails[0].replace("\\s+".toRegex(), "")
                val displayName = contactItem.displayName.replace("\\s+".toRegex(), "")
                if (email != displayName) {
                    holder.tvNumber.setVisibility(View.VISIBLE)
                    holder.tvNumber.setText(email)
                } else {
                    holder.tvNumber.setVisibility(View.GONE)
                }
            } else {
                holder.tvNumber.setVisibility(View.GONE)
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vRoundLetterView = itemView.vRoundLetterView
        val ivSelectedState = itemView.ivSelectedState
        val tvContactName = itemView.tvContactName
        val tvNumber = itemView.tvNumber
    }

    override fun getItemCount(): Int {
        return arrList.size
    }

    fun updateList(arrList: List<ContactResult>)
    {
        this.arrList = arrList
        notifyDataSetChanged()
    }

}