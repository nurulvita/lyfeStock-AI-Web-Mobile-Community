package com.example.applyfestox.data.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "detection_results")
data class DetectionResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val disease: String,
    val confidence: Float,
    val imageUri: String
)

@Dao
interface DetectionResultDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResult(result: DetectionResult)

    @Query("SELECT * FROM detection_results ORDER BY date DESC")
    fun getAllResults(): LiveData<List<DetectionResult>>
}
