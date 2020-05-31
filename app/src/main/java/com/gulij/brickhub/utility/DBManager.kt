package com.gulij.brickhub.utility

import android.content.ContentValues
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

    private fun executeSql(sql: String, selectionArgs: Array<String>? = null) {
        val cursor = db.rawQuery(sql, selectionArgs)
        cursor.close()
    }

    private fun executeSqlForResult(
        sql: String,
        selectionArgs: Array<String>? = null
    ): HashMap<String, String?>? {
        val cursor = db.rawQuery(sql, selectionArgs)
        var result: HashMap<String, String?>? = HashMap()
        if (cursor.moveToFirst()) {
            for (x in 0 until cursor.columnCount) {
                result!![cursor.getColumnName(x)] = cursor.getString(x)
            }
        } else {
            result = null
        }
        cursor.close()
        return result
    }

    private fun <T> executeSqlForArrayList(
        sql: String,
        selectionArgs: Array<String>? = null,
        converter: (Cursor) -> T
    ): ArrayList<T> {
        val cursor = db.rawQuery(sql, selectionArgs)
        val list = ArrayList<T>()
        while (cursor.moveToNext()) {
            list.add(converter.invoke(cursor))
        }
        cursor.close()
        return list
    }

    fun addProject(projectName: String): Int {
        val result =
            executeSqlForResult("select coalesce(max(id),0)+1,strftime(\"%s\",\"now\") from Inventories")!!.values.toList()
        db.insert("Inventories", null, ContentValues().apply {
            put("id", result[0])
            put("Name", projectName)
            put("Active", 1)
            put("LastAccessed", result[1])
        })
        return result[0]!!.toInt()
    }

    fun addPartFromItem(item: Item, projectId: Int) {
        val result = executeSqlForResult(
            "select Parts.TypeID,Parts.id from Parts where Code=?",
            arrayOf(item.itemId.toString())
        )?.values?.toList() ?: return
        val id =
            executeSqlForResult("select coalesce(max(InventoriesParts.id),0)+1 from InventoriesParts")!!.values.toList()[0]!!.toInt()
        db.insert("InventoriesParts", null, ContentValues().apply {
            put("id", id)
            put("InventoryID", projectId)
            put("TypeID", result[0])
            put("ItemID", result[1])
            put("QuantityInSet", item.qty)
            put("QuantityInStore", 0)
            put("ColorID", item.color)
            put("Extra", 0)
        })
    }

    fun getProjectIds(): ArrayList<Int> {
        return executeSqlForArrayList("select * from Inventories order by LastAccessed desc") {
            it.getInt(0)
        }
    }

    fun getProject(projectId: Int): HashMap<String, String?>? {
        return executeSqlForResult(
            "select Name from Inventories where id=?",
            arrayOf(projectId.toString())
        )
    }

    fun updateProject(projectId: Int) {
        executeSql(
            "update Inventories set LastAccessed=CURRENT_TIMESTAMP where id=?",
            arrayOf(projectId.toString())
        )
    }

    fun getPartIds(projectId: Int): ArrayList<Int> {
        return executeSqlForArrayList(
            "select id from InventoriesParts where InventoryID=?",
            arrayOf(projectId.toString())
        ) {
            it.getInt(0)
        }
    }

    fun getPart(partId: Int): HashMap<String, String?>? {
        return executeSqlForResult(
            "select Parts.Name as name, Colors.Name as color, Parts.Code as code from Codes left join Colors on Codes.ColorID = Colors.id left join Parts on Codes.id = Parts.id where Codes.id=?",
            arrayOf(partId.toString())
        )
    }

    fun getPartAmount(partId: Int): HashMap<String, String?>? {
        return executeSqlForResult(
            "select QuantityInStore,QuantityInSet from InventoriesParts where id=?",
            arrayOf(partId.toString())
        )
    }

    fun changePartAmount(partId: Int, amountDelta: Int): HashMap<String, String?>? {
        val quantity = getPartAmount(partId)!!.values.toList()[0]!!.toInt()
        val maxQuantity = getPartAmount(partId)!!.values.toList()[1]!!.toInt()
        db.update(
            "InventoriesParts", ContentValues().apply {
                put("QuantityInStore", (quantity + amountDelta).coerceIn(0, maxQuantity))
            }, "id=?", arrayOf(partId.toString())
        )
        return getPartAmount(partId)
    }
}