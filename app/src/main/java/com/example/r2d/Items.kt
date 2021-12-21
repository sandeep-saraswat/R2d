package com.example.r2d

class Items {
    var itemname: String? = null
        private set
    var itemcategory: String? = null
        private set
    var itemprice: String? = null
        private set
    var itembarcode: String? = null
        private set

    constructor() {}
    constructor(
        itemname: String?,
        itemcategory: String?,
        itemprice: String?,
        itembarcode: String?
    ) {
        this.itemname = itemname
        this.itemcategory = itemcategory
        this.itemprice = itemprice
        this.itembarcode = itembarcode
    }
}