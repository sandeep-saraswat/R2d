package com.example.r2d.database

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
}