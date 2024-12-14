package com.example.applyfestox.data.sqlite

import android.content.Context
import android.os.Environment
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date

fun createImageFile(context: Context): File {
    // Membuat nama file gambar unik
    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

    return File.createTempFile(
        "JPEG_${timeStamp}_", // Nama file prefix
        ".jpg",              // Ekstensi file
        storageDir           // Direktori penyimpanan
    ).apply {
        // Opsional: Tambahkan file ke MediaStore (agar muncul di galeri)
        absolutePath
    }
}
