package com.example.applyfestox.data.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.OptIn
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import java.io.File
import java.io.FileOutputStream

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    @OptIn(UnstableApi::class)
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

        val createlaporkankendalaTable = """
            CREATE TABLE $TABLE_LAPOR (
                $COLUMN_NAMA TEXT NOT NULL,
                $COLUMN_ALAMATEMAIL TEXT NOT NULL,
                $COLUMN_NOMOR TEXT NOT NULL,
                $COLUMN_KENDALA TEXT NOT NULL
            );
        """
        db?.execSQL(createlaporkankendalaTable)

        val createDiagnosaTable = """
            CREATE TABLE $TABLE_DIAGNOSA (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_USER_ID INTEGER NOT NULL,
                $COLUMN_WAKTU TEXT NOT NULL,
                $COLUMN_HASIL TEXT NOT NULL,
                $COLUMN_GAMBAR TEXT NOT NULL,
                FOREIGN KEY($COLUMN_USER_ID) REFERENCES $TABLE_USERS($COLUMN_USER_ID) ON DELETE CASCADE
            );
        """.trimIndent()
        db?.execSQL(createDiagnosaTable)
        Log.d("DatabaseHelper", "Table $TABLE_DIAGNOSA created.")
    }

    fun updateUser(userId: Int, email: String, name: String, birthDate: String, photo: String?): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_EMAIL, email)
            put(COLUMN_NAMAPENGGUNA, name)
            put(COLUMN_TTL, birthDate)
            if (photo != null) put(COLUMN_PHOTO, photo)
        }
        return db.update(TABLE_USERS, values, "$COLUMN_USER_ID = ?", arrayOf(userId.toString())) > 0
    }


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_MANAGEMENT")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LAPOR")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_DIAGNOSA")
        onCreate(db)
    }

    fun loginUser(email: String, password: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_USER_ID FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        db.rawQuery(query, arrayOf(email, password)).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))
            }
        }
        return null // Login gagal
    }


    fun getUserId(email: String, password: String): Int? {
        val db = this.readableDatabase
        val query = "SELECT $COLUMN_USER_ID FROM $TABLE_USERS WHERE $COLUMN_EMAIL = ? AND $COLUMN_PASSWORD = ?"
        db.rawQuery(query, arrayOf(email, password)).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_USER_ID))
            }
        }
        return null
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

    fun getDiagnosaData(userId: Int): List<Diagnosa> {
        val diagnosaList = mutableListOf<Diagnosa>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_DIAGNOSA WHERE $COLUMN_USER_ID = ?"
        db.rawQuery(query, arrayOf(userId.toString())).use { cursor ->
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val waktu = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_WAKTU))
                val hasil = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_HASIL))
                val gambar = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GAMBAR))
                diagnosaList.add(Diagnosa(id, waktu, hasil, gambar))
            }
        }
        return diagnosaList
    }

    fun deleteDiagnosa(userId: Int, diagnosaId: Int): Boolean {
        val db = writableDatabase
        return try {
            val result = db.delete(
                TABLE_DIAGNOSA,
                "$COLUMN_ID = ? AND $COLUMN_USER_ID = ?",
                arrayOf(diagnosaId.toString(), userId.toString())
            )
            result > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }



    fun updateUserPhoto(userId: Int, photoUri: String): Boolean {
        if (photoUri.isEmpty()) {
            return false
        }

        val db = writableDatabase
        return try {
            val values = ContentValues().apply {
                put("photo", photoUri)
            }
            val result = db.update("users", values, "id = ?", arrayOf(userId.toString()))
            result > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }
    fun updateUserPassword(userId: Int, newPassword: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_PASSWORD, newPassword)
        }
        return try {
            val result = db.update(TABLE_USERS, values, "$COLUMN_USER_ID = ?", arrayOf(userId.toString()))
            result > 0
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }

    fun savePhotoToInternalStorage(context: Context, imageName: String, bitmap: Bitmap): String? {
        return try {
            val file = File(context.filesDir, "$imageName.jpg")
            val outputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
            file.absolutePath // Return full path of the file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun saveDiagnosa(
        context: Context,
        userId: Int, // User ID yang sedang login
        waktu: String,
        hasil: String,
        gambarBitmap: Bitmap
    ): Boolean {
        val db = writableDatabase
        return try {
            val imageName = "diagnosa_${System.currentTimeMillis()}"
            val imagePath = savePhotoToInternalStorage(context, imageName, gambarBitmap)
            if (imagePath != null) {
                val values = ContentValues().apply {
                    put(COLUMN_USER_ID, userId) // Simpan berdasarkan userId
                    put(COLUMN_WAKTU, waktu)
                    put(COLUMN_HASIL, hasil)
                    put(COLUMN_GAMBAR, imagePath)
                }
                db.insert(TABLE_DIAGNOSA, null, values) > 0
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        } finally {
            db.close()
        }
    }


    fun loadPhotoFromInternalStorage(imagePath: String): Bitmap? {
        return try {
            BitmapFactory.decodeFile(imagePath)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun saveLaporanKendala(email: String, name: String, phoneNumber: String, issue: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("email", email)
            put("name", name)
            put("phone_number", phoneNumber)
            put("issue", issue)
        }
        val result = db.insert("laporan_kendala", null, values)
        return result != -1L
    }


    companion object {
        const val DATABASE_NAME = "appLyfestox.db"
        const val DATABASE_VERSION = 3

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

        const val TABLE_LAPOR = "laporkendala"
        const val COLUMN_NAMA = "nama"
        const val COLUMN_ALAMATEMAIL = "email"
        const val COLUMN_NOMOR = "nomor"
        const val COLUMN_KENDALA = "kendala"

        const val TABLE_DIAGNOSA = "diagnosa"
        const val COLUMN_ID = "id"
        const val COLUMN_WAKTU = "waktu"
        const val COLUMN_HASIL = "hasil"
        const val COLUMN_GAMBAR = "gambar"
    }
}

data class User(
    val id: Int,
    val email: String,
    val name: String,
    val birthDate: String,
    val photo: String?,
)

data class Diagnosa(
    val id: Int,
    val waktu: String,
    val hasil: String,
    val gambar: String
)

data class Lapor(
    val nama: String,
    val email: String,
    val nomor: String,
    val kendala: String
)