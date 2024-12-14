package com.example.applyfestox.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_EMAIL TEXT NOT NULL UNIQUE,
                $COLUMN_NAMAPENGGUNA TEXT NOT NULL,
                $COLUMN_TTL TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_PHOTO TEXT NOT NULL
            );
        """
        db?.execSQL(createUserTable)

        val createManagementTable = """
            CREATE TABLE $TABLE_MANAGEMENT (
                $COLUMN_ITEM_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_ITEM_NAME TEXT NOT NULL,
                $COLUMN_ITEM_DESCRIPTION TEXT
            );
        """
        db?.execSQL(createManagementTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MANAGEMENT")
        onCreate(db)
    }

    fun loginUser(email: String, password: String): Boolean {
        if (email.isBlank() || password.isBlank()) return false

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        db.rawQuery(query, arrayOf(email, password)).use { cursor ->
            return cursor.count > 0
        }
    }

    fun registerUser(email: String, username: String, birthDate: String, password: String, photo: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_NAMAPENGGUNA, username)
            put(COLUMN_TTL, birthDate)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_PHOTO, photo)
        }
        return try {
            db.insert(TABLE_USERS, null, values) > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getUserData(email: String): User? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ?"
        db.rawQuery(query, arrayOf(email)).use { cursor ->
            if (cursor.moveToFirst()) {
                return User(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMAPENGGUNA)),
                    birthDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TTL)),
                    photo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO))
                )
            }
        }
        return null
    }

    companion object {
        const val DATABASE_NAME = "appLyfestox.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_USER_ID = "user_id"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_NAMAPENGGUNA = "namapengguna"
        const val COLUMN_TTL = "ttl"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_PHOTO = "photo"

        const val TABLE_MANAGEMENT = "management"
        const val COLUMN_ITEM_ID = "item_id"
        const val COLUMN_ITEM_NAME = "item_name"
        const val COLUMN_ITEM_DESCRIPTION = "item_description"
    }
}

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val birthDate: String,
    val photo: String?
)
