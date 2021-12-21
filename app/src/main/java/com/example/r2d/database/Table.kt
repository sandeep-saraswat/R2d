package com.example.r2d.database

import androidx.room.Entity
import androidx.room.PrimaryKey

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