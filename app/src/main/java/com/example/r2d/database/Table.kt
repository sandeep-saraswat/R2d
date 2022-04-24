package com.example.r2d.database

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import java.io.Serializable

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

@Entity
data class ProductTable(
    @PrimaryKey(autoGenerate = true)
    var productId: Long,
    var productName: String = "",
    var categoryId: String = "",/*it's dropdown so take id and name*/
    var categoryName: String = "",
    var productCode: String = "",
    var brandId:String = "",/*it's dropdown so take id and name*/
    var brandName:String = "",
    var productUnitId:String = "",/*it's dropdown so take id and name*/
    var productUnitName:String = "",
    var productPrice:String = "",
    var productTaxId:String = "",/*it's dropdown so take id and name*/
    var productTaxName:String = "",
    var discount:String = "",
    var productImage: Bitmap? = null,
    var stock:String = "",
):Serializable


@Entity
data class CustomerTable(
    @PrimaryKey(autoGenerate = true)
    var customerId: Long,
    var customerName: String = "",
    var phone: String = "",
    var city: String = "",
    var emailAddress: String = "",
    var zipcode:String = "",

):Serializable

/*
@Entity
data class Test(
    @PrimaryKey
    @ColumnInfo(name = "image_id")
    var id:Int,

    @ColumnInfo(name = "imageList")
    var image:String
)*/
