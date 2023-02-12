package com.smarttoolfactory.data.di

import android.app.Application
import androidx.room.Room
import com.smarttoolfactory.data.db.AppDatabase
import com.smarttoolfactory.data.db.dao.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "transaction.db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(appDatabase: AppDatabase): TransactionDao =
        appDatabase.transactionDao()
}