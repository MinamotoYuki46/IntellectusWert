package com.intellectuswert.data.local.dao

import androidx.room.*
import com.intellectuswert.data.local.entity.PredictionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PredictionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prediction: PredictionEntity)

    @Query("SELECT * FROM predictions ORDER by timestamp DESC")
    fun getAll(): Flow<List<PredictionEntity>>

    @Query("SELECT * FROM predictions where id = :id")
    suspend fun getById(id: Int): PredictionEntity?

    @Query("DELETE FROM predictions WHERE id = :id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM predictions")
    suspend fun clearAll()

}