package com.example.r2d.database

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
}