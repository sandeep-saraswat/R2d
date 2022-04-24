package com.example.r2d.database

import android.media.Image
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AppDao {
    @Query("SELECT * FROM ItemData")
    fun getAllData(): List<ItemData>

    @Query("SELECT * FROM ItemData WHERE prodId LIKE :prodId")
    fun findByTitle(prodId: String): ItemData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: ItemData)

    @Delete
    fun delete(todo: ItemData)

    @Update
    fun updateTodo(vararg todos: ItemData)

    // region ====== Template Table dao
    @Query("SELECT * FROM TemplateData")
    fun getAllTemplate(): List<TemplateData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTemplate(template: TemplateData):Long

    @Delete
    fun deleteTemplate(template: TemplateData)

    // end region


    // region ====== Template Table dao
    @Query("SELECT * FROM ContactGroupData")
    fun getAllContactGroup(): List<ContactGroupData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContactGroup(contactGroupData: ContactGroupData):Long

    @Delete
    fun deleteContactGroup(contactGroupData: ContactGroupData)

    // end region

    // region ====== ProductTable Table dao
    @Query("SELECT * FROM ProductTable")
    fun getAllProduct(): MutableList<ProductTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(productTable: ProductTable):Long

    @Delete
    fun deleteProduct(productTable: ProductTable)

    // end region

    // region ====== CustomerTable Table dao
    @Query("SELECT * FROM CustomerTable")
    fun getAllCustomer(): MutableList<CustomerTable>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCustomerTable(customerTable: CustomerTable):Long

    @Delete
    fun deleteCustomerTable(customerTable: CustomerTable)

    // end region

   /* @Insert
    fun insert(vararg images: Test?)

    @Query("SELECT * FROM Test")
    fun getAllImage(): List<Image?>?

    @Query("SELECT * FROM Test where image_id = :imageId")
    fun getImageByImageId(imageId: Int): List<Image?>?*/
}