package com.example.r2d.database

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.example.r2d.wafflecopter.multicontactpicker.ContactResult
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream


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

    @TypeConverter
    fun BitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
        val b: ByteArray = baos.toByteArray()
        val temp: String = android.util.Base64.encodeToString(b, android.util.Base64.DEFAULT)
        return if (temp == null) {
            null
        } else temp
    }

    @TypeConverter
    fun StringToBitMap(encodedString: String?): Bitmap? {
        return try {
            val encodeByte: ByteArray = android.util.Base64.decode(encodedString, android.util.Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.size)
            bitmap
        } catch (e: Exception) {
            e.message
            null
        }
    }
}