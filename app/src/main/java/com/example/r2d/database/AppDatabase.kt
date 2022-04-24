package com.example.r2d.database

import android.content.Context
import android.media.Image
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(
    entities = [ItemData::class, TemplateData::class, ContactGroupData::class, ProductTable::class,CustomerTable::class],
    version = 1
)
@TypeConverters(Convertors::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun appDao(): AppDao

    companion object {
        @Volatile private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            AppDatabase::class.java, "todo-list.db")
            .build()
    }
}


