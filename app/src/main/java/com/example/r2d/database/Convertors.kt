package com.example.r2d.database

import androidx.room.TypeConverter
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class Convertors {

    @TypeConverter
    fun fromContactResultToString(value: List<ContactResult>?):String{

        val tempListInString = Gson().toJson(
            value,
            object : TypeToken<List<ContactResult>>() {}.type
        ) ?: ""
       // saveString(LAST_BATCH, tempBatchDataList)

        return tempListInString
    }

    @TypeConverter
    fun fromStringToContactResult(value:String): List<ContactResult>? {

        val tempList = Gson().fromJson<List<ContactResult>>(
            value,
            object : TypeToken<List<ContactResult>>() {}.type
        )
        return tempList

    }
}