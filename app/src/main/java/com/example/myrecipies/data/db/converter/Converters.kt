package com.example.myrecipies.data.db.converter

import androidx.room.TypeConverter
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        // If value is null, return null, otherwise create a Date from the timestamp
        return value?.let { Date(it) } // Correct usage of java.util.Date
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        // If date is null, return null, otherwise get its time (timestamp)
        return date?.time
    }

    // Keep any other converters you might have (e.g., for List<String>)
    // @TypeConverter
    // fun fromStringList(value: List<String>?): String? { ... }
    // @TypeConverter
    // fun toStringList(value: String?): List<String>? { ... }
}