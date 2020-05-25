package com.gulij.brickhub.utility

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.gulij.brickhub.models.Brick
import java.io.File

object DBManager {
    lateinit var db: SQLiteDatabase

    fun init(context: Context) {
        if (this::db.isInitialized) {
            return
        }

        val dbFile = File(context.getExternalFilesDir(null), "BrickList.db")
        if (!dbFile.exists()) {
            val input = context.assets.open("BrickList.db")
            dbFile.createNewFile()
            val output = dbFile.outputStream()
            val buffer = ByteArray(1024)
            var read = input.read(buffer)
            while (read != -1) {
                output.write(buffer, 0, read)
                read = input.read(buffer)
            }
            output.close()
        }

        db = SQLiteDatabase.openDatabase(dbFile.path, null, 0)
    }

    fun getBrickByItemId(itemCode: String): Brick? {
        val cursor = db.rawQuery("select Name from Parts where Code=\'$itemCode\'", null)
        val brick = if (cursor.moveToFirst()) {
            Brick(cursor.getString(0))
        } else {
            null
        }
        cursor.close()
        return brick
    }
}