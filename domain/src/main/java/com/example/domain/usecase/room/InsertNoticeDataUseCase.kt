package com.example.domain.usecase.room

import com.example.domain.model.local.NoticeData
import com.example.domain.repo.RoomRepository

class InsertNoticeDataUseCase(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(insertRoomData: NoticeData) =
        roomRepository.insertData(insertRoomData)
}