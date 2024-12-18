//package com.example.applyfestox.data.Room
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.Query
//import com.example.applyfestox.data.model.DetectionHistory
//
//@Dao
//interface DetectionResultDao {
//    @Insert
//    suspend fun insertDetection(detection: DetectionHistory)
//
//    @Query("SELECT * FROM detection_history ORDER BY id DESC")
//    suspend fun getAllDetections(): List<DetectionHistory>
//}
//
