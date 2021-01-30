package com.demo.demoapplication.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.demoapplication.room.converters.Converters
import com.demo.demoapplication.room.converters.DataConverter

@Database(
    entities = arrayOf(AcronymEntity::class),
    version = 3
)
@TypeConverters(Converters::class,DataConverter::class)
abstract class AcronymDatabase: RoomDatabase() {

    abstract fun getAcronymDao(): AcronymDao

    companion object {
        @Volatile private var instance: AcronymDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AcronymDatabase::class.java,
            "notedatabase"
        ).build()
    }
}