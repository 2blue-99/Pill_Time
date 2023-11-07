package com.example.data.di

import com.example.domain.repo.*
import com.example.domain.usecase.room.DeleteNoticeDataUseCase
import com.example.domain.usecase.room.GetNoticeDataListUseCase
import com.example.domain.usecase.room.InsertNoticeDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    //ROOM
    @Provides
    @Singleton
    fun provideDeleteRoomDataUseCase(repo : RoomRepository) : DeleteNoticeDataUseCase =
        DeleteNoticeDataUseCase(repo)


    @Provides
    @Singleton
    fun provideGetRoomDataListUseCase(repo : RoomRepository) : GetNoticeDataListUseCase =
        GetNoticeDataListUseCase(repo)


    @Provides
    @Singleton
    fun provideInsertDataRoomUseCase(repo : RoomRepository) : InsertNoticeDataUseCase =
        InsertNoticeDataUseCase(repo)


//    @Provides
//    @Singleton
//    fun provideUpdateRoomUseCAse(repo : RoomRepository) : UpdateRoomDataUseCase =
//        UpdateRoomDataUseCase(repo)
}