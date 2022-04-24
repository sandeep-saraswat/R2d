package com.example.r2d.utils

import com.example.r2d.R

enum class EnumCategory(var id:Int, var categoryName:String){
    CATEGORY(0,"Please Select Category"),
    COMPUTER(1,"Computer"),
    TELEVISION(2,"Television"),
    SHOES(3,"Shoes"),
    FRUITS(4,"Fruits"),
}

enum class EnumBrand(var id:Int, var brandName:String){
    BRAND(0,"Please Select Brand"),
    DELL(1,"Dell"),
    ACER(2,"Acer"),
    ASSUS(3,"Assus"),
    HP(4,"HP"),
}

enum class EnumProductUnit(var id:Int, var productUnitName:String, var shortName:String){
    PRODUCT_UNIT(0,"Please Select Product Unit",""),
    KILOGRAM(1,"Kilogram","kg"),
    CENTIMETER(2,"Centimeter","cm"),
    QUANTITY(3,"Quantity","qty"),
    GRAM(4,"Gram","g"),
    DOZEN(5,"Dozen","pc"),
    MILLIMETER(6,"Millimeter","mm"),
    YARD(7,"Yard","y"),
    MILLILITER(8,"Milliliter","ml"),

}

enum class EnumProductTax(var id:Int, var productTaxName:String){
    PRODUCT_TAX(0,"Please Select Product Tax"),
    VAT_10(1,"Vat @10%"),
    VAT_9(2,"Vat @19%"),
    VAT_11(3,"Vat @11%"),
    VAT_12(4,"Vat @12%"),

}

enum class EnumAction(var id:Int, var actionName:String){
    ACTION(0,"Action"),
    EDIT(1,"Edit"),
    DELETE(2,"Delete"),

}

enum class EnumDashboard(var id:Int, var icon:Int,var tittle:String,var index:Int){
    ADD_Product(1, R.drawable.ic_add_circle_new,"Add Product",1),
    DELETE_Product(2,R.drawable.ic_delete_new,"Delete Product",2),
    CUSTOMER(2,R.drawable.ic_customer_new,"Customer",3),
    VIEW_PRODUCT(3,R.drawable.ic_find_replace_new,"View Product",4),
    VIEW_INVENTORY(4,R.drawable.ic_inventory_new,"View Inventory",5),
    WHATSAPP(5,R.drawable.whatsapp,"Whatsapp",6),
    MESSAGING(6,R.drawable.ic_msg_new,"Messaging",7),
    CREATE_GROUP(7,R.drawable.ic_create_group_new,"Create Group",8),
    CREATE_TEMPLETE(8,R.drawable.ic_template_new,"Create Templete",9),

}