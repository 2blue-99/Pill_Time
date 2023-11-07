package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.local.LocalDatabase
import com.example.data.local.migration_2_3
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {

//    @Singleton
//    @Provides
//    fun providePreferenceManager(@ApplicationContext context: Context): PreferenceManager =
//        PreferenceManager(
//            context.getSharedPreferences(
//                context.packageName,
//                Context.MODE_PRIVATE
//            )
//        )


    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext appContext: Context): LocalDatabase =
        Room.databaseBuilder(appContext, LocalDatabase::class.java, "myDb")
            .addMigrations(migration_2_3)
            .fallbackToDestructiveMigration()
            .build()

}