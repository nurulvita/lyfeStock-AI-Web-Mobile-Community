//package com.example.applyfestox.data.Room
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import com.example.applyfestox.data.model.DetectionHistory
//import com.example.applyfestox.data.model.DetectionResultDao
//
//@Database(entities = [DetectionHistory::class], version = 1)
//abstract class AppDatabase : RoomDatabase() {
//    abstract fun detectionResultDao(): DetectionResultDao
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
//                    "detection_results_db"
//                ).build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}
