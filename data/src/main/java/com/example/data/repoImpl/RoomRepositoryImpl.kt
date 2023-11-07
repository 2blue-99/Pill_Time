package com.example.data.repoImpl

import com.example.data.local.dao.MyDao
import com.example.data.local.entity.MyEntity
import com.example.data.local.entity.toDomainEntity
import com.example.domain.model.local.NoticeData
import com.example.domain.repo.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomDao: MyDao
) : RoomRepository {

    override suspend fun insertData(list: NoticeData) {
        roomDao.insertData(
            MyEntity(
                uid = list.uid,
                time = list.time,
                month = list.month,
                day = list.day,
                isChecked = list.isChecked,
                pills = list.pills
            )
        )
    }

    override suspend fun getAllData(): MutableList<NoticeData> =
        roomDao.getAllData().map { it.toDomainEntity() }.toMutableList()


    override suspend fun deleteData(uid: String): Int =
        roomDao.deleteData(uid = uid)


//    override suspend fun updateData(list: ): Int =
//        roomDao.updateData(
//            MyEntity(
//                uid = list.uid,
//                billSubmitTime = list.billSubmitTime,
//                cardName = list.cardName,
//                amount = list.storeAmount,
//                pictureName = list.storeName,
//                memo = list.memo,
//                picture = list.file
//            )
//        )
}