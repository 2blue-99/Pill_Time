package com.example.domain.repo

import com.example.domain.model.local.NoticeData
/**
 * 2023-02-15
 * pureum
 */
interface RoomRepository {
    suspend fun insertData(list: NoticeData)
    suspend fun getAllData():MutableList<NoticeData>
    suspend fun deleteData(uid: String) : Int
//    suspend fun updateData(list: RoomData): Int
}