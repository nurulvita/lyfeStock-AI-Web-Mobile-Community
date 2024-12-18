package com.example.applyfestox.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection_history")
data class DetectionHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val disease: String,
    val confidence: Float,
    val imageUri: String,
    val date: String
)
