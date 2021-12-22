package com.example.r2d.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult

@Entity
data class  ItemData(
    @PrimaryKey
    var prodId: String,
    var prodName: String,
    var prodCategory: String,
    var prodPrice: String,
    var quantity:String,
    var date:String
)

@Entity
data class TemplateData(
    @PrimaryKey(autoGenerate = true)
    var tempId: Long,
    var template: String
)

@Entity
data class ContactGroupData(
    @PrimaryKey(autoGenerate = true)
    var groupId: Long,
    var groupName: String,
    var groupContact: List<ContactResult>?
)