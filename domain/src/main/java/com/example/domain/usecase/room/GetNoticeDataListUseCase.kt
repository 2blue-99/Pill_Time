package com.example.domain.usecase.room

import com.example.domain.model.local.NoticeData
import com.example.domain.repo.RoomRepository

class GetNoticeDataListUseCase(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(): MutableList<NoticeData> =
        roomRepository.getAllData()
}