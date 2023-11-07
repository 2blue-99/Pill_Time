package com.example.data.local.dao

import androidx.room.*
import com.example.data.local.entity.MyEntity
import okhttp3.MultipartBody

@Dao
interface MyDao {

    @Query("Select * From NoticeTable")
    fun getAllData(): MutableList<MyEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(list: MyEntity)

    @Query("Delete from NoticeTable where uid = :uid")
    fun deleteData(uid: String): Int

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateData(list: MyEntity): Int
}