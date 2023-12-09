package com.example.madassignment

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast


const val DATABASE_NAME = "MyDB"
const val TABLE_NAME = "Contacts"
const val COL_NAME = "name"
const val COL_phone = "number"
const val COL_ID = "id"

class DataBaseHandler(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $COL_NAME VARCHAR(256)," +
                    " $COL_phone INTEGER)"

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database upgrade if needed
    }

    fun insertData(user: User) {
        val db = this.writableDatabase
        val cv = ContentValues().apply {
            put(COL_NAME, user.name)
            put(COL_phone, user.phone)
        }

        val result = db.insert(TABLE_NAME, null, cv)
        if (result == -1L) {
            Toast.makeText(context, "Failed to insert data", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Data inserted successfully", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    @SuppressLint("Range")
    fun readData(): MutableList<User> {
        val list: MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)

        if (result.moveToFirst()) {
            do {
                val user = User().apply {
                    id = result.getInt(result.run { getColumnIndex(COL_ID) })
                    name = result.getString(result.getColumnIndex(COL_NAME))
                    phone = result.getInt(result.getColumnIndex(COL_phone))
                }
                list.add(user)
            } while (result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }


fun deleteData(userId: Int?): Boolean {
    if (userId == null) {
        // If userId is null, do nothing and return false
        return false
    }
    val db = this.writableDatabase
    val result = db.delete(TABLE_NAME, "$COL_ID=?", arrayOf(userId.toString()))
    db.close()

    return result != -1
}
    fun updateData(user: User?): Boolean {
        if(user==null)
        {
            return false;
        }
            val db = this.writableDatabase

            val cv = ContentValues().apply {
                put(COL_NAME, user.name)
                put(COL_phone, user.phone)
            }

            val result = db.update(TABLE_NAME, cv, "$COL_ID=?", arrayOf(user.id.toString()))

            db.close()

            return result != -1
        }


//    fun updateData() {
//        val db = this.writableDatabase
//        val query = "SELECT * FROM $TABLE_NAME"
//        val result = db.rawQuery(query, null)
//
//        if (result.moveToFirst()) {
//            do {
//                val cv = ContentValues().apply {
//                    put(COL_phone, result.getInt(result.run { getColumnIndex(COL_phone) }) + 1)
//                }
//
//                db.update(
//                    TABLE_NAME, cv, "$COL_ID = ? AND $COL_NAME = ?",
//                    arrayOf(
//                        result.getString(result.run { getColumnIndex(COL_ID) }),
//                        result.getString(result.run { getColumnIndex(COL_NAME) })
//                    )
//                )
//            } while (result.moveToNext())
//        }
//
//        result.close()
//        db.close()
//    }
}
