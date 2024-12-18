import android.content.ContentValues
import android.content.Context
import com.example.applyfestox.data.sqlite.DatabaseHelper

fun saveDiagnosisToDatabase(context: Context, waktu: String, hasil: String, gambar: String): Boolean {
    val dbHelper = DatabaseHelper(context)
    val db = dbHelper.writableDatabase

    val contentValues = ContentValues().apply {
        put(DatabaseHelper.COLUMN_WAKTU, waktu)
        put(DatabaseHelper.COLUMN_HASIL, hasil)
        put(DatabaseHelper.COLUMN_GAMBAR, gambar)
    }

    val result = db.insert(DatabaseHelper.TABLE_DIAGNOSA, null, contentValues)
    db.close()

    // Cek hasil penyimpanan
    return result != -1L
}

