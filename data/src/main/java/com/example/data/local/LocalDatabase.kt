package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.data.local.dao.MyDao
import com.example.data.local.entity.MyEntity

/**
 * 2023-02-15
 * pureum
 */
@Database(entities = [MyEntity::class], version = 3, exportSchema = false)
abstract class LocalDatabase :RoomDatabase(){
    abstract fun myDatabase():MyDao
}

val migration_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE NoticeTable Add COLUMN uid TEXT NOT NULL DEFAULT 0"
        )
    }
}