package com.intellectuswert.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.intellectuswert.data.local.dao.PredictionDao
import com.intellectuswert.data.local.entity.PredictionEntity


@Database(
    entities = [PredictionEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun predictionDao(): PredictionDao
}