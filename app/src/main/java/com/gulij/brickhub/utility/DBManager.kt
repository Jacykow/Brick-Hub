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
        if (!cursor1.moveToFirst()) {
            cursor1.close()
            return null
        }
        val itemId = cursor1.getInt(0)
        cursor1.close()

        val cursor2 =
            db.rawQuery("select TypeID, id from Parts where Code=\'${item.itemId}\'", null)
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

        if (brick != null) {
            //val cursor3 = db.rawQuery("insert into InventoriesParts (id,InventoryId,TypeID,ItemID,QuantityInSet,QuantityInStore,ColorID) values ()", null)

        }

        return brick
    }

    fun getBrickNameAndColor(brick: Brick): Pair<String, String> {
        val cursor = db.rawQuery(
            "select Parts.Name, Colors.Name from Codes left join Colors on Codes.ColorID = Colors.id left join Parts on Codes.id = Parts.id where Codes.id=${brick.itemId}",
            null
        )
        cursor.moveToFirst()
        val nameAndColor = Pair(cursor.getString(0), cursor.getString(1))
        cursor.close()
        return nameAndColor
    }
}