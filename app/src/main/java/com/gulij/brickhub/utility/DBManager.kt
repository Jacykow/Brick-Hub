package com.gulij.brickhub.utility

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.gulij.brickhub.models.Brick
import com.gulij.brickhub.models.Item
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

    fun getBrickByItem(item: Item, projectId: Int): Brick? {
        val cursor1 = db.rawQuery("select coalesce(max(id),0)+1 from InventoriesParts", null)
        if(!cursor1.moveToFirst()){
            cursor1.close()
            return null
        }
        val itemId = cursor1.getInt(0)
        cursor1.close()

        val cursor2 = db.rawQuery("select TypeID, id from Parts where Code=\'$item.code\'", null)
        val brick = if (cursor2.moveToFirst()) {
            Brick(
                itemId,
                projectId,
                cursor2.getInt(0),
                cursor2.getInt(1),
                item.qty!!.toInt(),
                0,
                item.color!!.toInt()
            )
        } else {
            null
        }

        cursor2.close()
        return brick
    }
}