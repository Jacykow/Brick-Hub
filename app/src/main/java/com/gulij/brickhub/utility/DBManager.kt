package com.gulij.brickhub.utility

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
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

    private fun executeSql(sql: String, onResult: (Cursor) -> Unit) {
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            onResult.invoke(cursor)
        }
        cursor.close()
    }

    private fun <T> executeSqlForArrayList(sql: String, converter: (Cursor) -> T): ArrayList<T> {
        val cursor = db.rawQuery(sql, null)
        val list = ArrayList<T>()
        if (cursor.moveToFirst()) {
            list.add(converter.invoke(cursor))
        }
        cursor.close()
        return list
    }

    fun addProject(projectName: String, onResult: (Cursor) -> Unit) {
        executeSql(
            "insert into Inventories select coalesce(max(id),0)+1,\'$projectName\',1,CURRENT_TIMESTAMP from Inventories",
            onResult
        )
    }

    fun addPartFromItem(item: Item, projectId: Int, onResult: (Cursor) -> Unit) {
        executeSql(
            "insert into InventoriesParts select coalesce(max(InventoriesParts.id),0)+1,$projectId,Parts.TypeID,Parts.id,${item.qty},0,${item.color},0 from Parts left join InventoriesParts where Parts.Code=\'${item.itemId}\'",
            onResult
        )
    }

    fun getProjectIds(): ArrayList<Int> {
        return executeSqlForArrayList("select * from Inventories order by LastAccessed desc") {
            it.getInt(0)
        }
    }

    fun getProject(projectId: Int, onResult: (Cursor) -> Unit) {
        executeSql(
            "select Name from Inventories where id=$projectId",
            onResult
        )
    }

    fun updateProject(projectId: Int, onResult: (Cursor) -> Unit) {
        executeSql(
            "update Inventories set LastAccessed=CURRENT_TIMESTAMP where id=$projectId",
            onResult
        )
    }

    fun getPartIds(projectId: Int): ArrayList<Int> {
        return executeSqlForArrayList("select id from InventoriesParts where InventoryID=$projectId") {
            it.getInt(0)
        }
    }

    fun getPart(partId: Int, onResult: (Cursor) -> Unit) {
        executeSql(
            "select Parts.Name, Colors.Name from Codes left join Colors on Codes.ColorID = Colors.id left join Parts on Codes.id = Parts.id where Codes.id=${partId}",
            onResult
        )
    }
}