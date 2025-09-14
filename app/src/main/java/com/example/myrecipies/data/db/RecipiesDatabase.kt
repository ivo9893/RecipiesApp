package com.example.myrecipies.data.db.entity

import android.content.Context
import android.drm.DrmConvertedStatus
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myrecipies.data.db.converter.Converters
import com.example.myrecipies.data.db.dao.CategoriesDao
import com.example.myrecipies.data.db.dao.RecipiesDao
import com.example.myrecipies.data.db.dao.UnitsDao

@Database(entities = [
    RecipeEntity::class,
    CategoriesEntity::class,
    UnitsEntity::class], version = 3)
@TypeConverters(Converters::class)
abstract class RecipiesDatabase : RoomDatabase() {


    abstract fun recipiesDao() : RecipiesDao
    abstract fun categoriesDao() : CategoriesDao
    abstract fun unitsDao() : UnitsDao

    companion object{
        @Volatile
        private var instance : RecipiesDatabase? = null

        fun getDatabase(context: Context) : RecipiesDatabase =
            instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it}
            }

        private fun buildDatabase (appContext: Context) =
            Room.databaseBuilder(appContext, RecipiesDatabase::class.java, "RecipiesDatabase")
                .fallbackToDestructiveMigration(false)
                .build()
    }
}