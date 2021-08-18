package com.example.wonhoi_seoul_subway_info_app_for_global.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wonhoi_seoul_subway_info_app_for_global.data.db.entity.*

@Database(
    entities = [StationEntity::class, SubwayEntity::class, StationSubwayCrossRefEntity::class, StationIdAndEngNameEntity::class],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun stationDao() : StationDao

    companion object {

        private const val DATABASE_NAME = "station.db"

        fun build(context: Context) : AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()
    }
}