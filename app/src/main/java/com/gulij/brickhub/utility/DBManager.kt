package com.gulij.brickhub.utility

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import java.io.File

object DBManager {
    lateinit var db: SQLiteDatabase

    fun init(context: Context) {
        val input = context.assets.open("BrickList.db")
        val dbFile = File(context.getExternalFilesDir(null), "BrickList.db")
        dbFile.createNewFile()
        val output = dbFile.outputStream()
        val buffer = ByteArray(1024)
        var read = input.read(buffer)
        while (read != -1) {
            output.write(buffer, 0, read)
            read = input.read(buffer)
        }
        output.close()

        db = SQLiteDatabase.openDatabase(dbFile.path, null, 0)
    }
}