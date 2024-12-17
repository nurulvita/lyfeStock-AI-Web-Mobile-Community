//package com.example.applyfestox.data.repository
//
//import com.example.applyfestox.data.Room.DetectionHistoryDao
//import com.example.applyfestox.data.model.DetectionHistory
//
//class DetectionRepository(private val dao: DetectionHistoryDao) {
//    suspend fun insertDetection(detection: com.example.applyfestox.screen.hasil.DetectionHistory) {
//        dao.insertDetection(detection)
//    }
//
//    suspend fun getAllDetections(): List<DetectionHistory> {
//        return dao.getAllDetections()
//    }
//}
