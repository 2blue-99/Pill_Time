package com.example.data.di

import com.example.data.local.LocalDatabase
import com.example.data.repoImpl.*
import com.example.domain.repo.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideRepoRoom(source: LocalDatabase): RoomRepository =
        RoomRepositoryImpl(source.myDatabase())


}