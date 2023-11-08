package com.example.domain.usecase.room

import com.example.domain.repo.RoomRepository

class DeleteNoticeDataUseCase(
    private val roomRepository: RoomRepository
) {
    suspend operator fun invoke(submitTime:String):Int =
        roomRepository.deleteData(submitTime)
}