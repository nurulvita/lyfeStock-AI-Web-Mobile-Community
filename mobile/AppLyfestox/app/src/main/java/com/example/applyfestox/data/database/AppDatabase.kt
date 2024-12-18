//package com.example.applyfestox.data.database
//
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.applyfestox.data.Room.DetectionHistoryDao
//import com.example.applyfestox.data.model.DetectionHistory
//
//@Database(entities = [DetectionHistory::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun detectionHistoryDao(): DetectionHistoryDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: AppDatabase? = null
//
//        fun getDatabase(context: Context): AppDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "detection_database"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
