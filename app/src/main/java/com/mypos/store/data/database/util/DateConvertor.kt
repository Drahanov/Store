package com.mypos.store.data.database.util

import androidx.room.TypeConverter
import java.util.Date

class DateConvertor {
    @TypeConverter
    fun toDate(dateLong: Long?): Date? {
        return dateLong?.let { Date(it) }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }
}