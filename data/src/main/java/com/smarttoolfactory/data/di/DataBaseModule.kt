package com.smarttoolfactory.data.di

import android.app.Application
import androidx.room.Room
import com.smarttoolfactory.data.datasource.LocalDataSource
import com.smarttoolfactory.data.datasource.LocalDataSourceImpl
import com.smarttoolfactory.data.datasource.TransientDataSource
import com.smarttoolfactory.data.datasource.TransientDataSourceImpl
import com.smarttoolfactory.data.db.AppDatabase
import com.smarttoolfactory.data.db.dao.TransactionDao
import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.data.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.LinkedList
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module(
    includes = [
        DatabaseModule::class
    ]
)
interface DataModule {
    @Singleton
    @Binds
    fun bindRepository(repository: TransactionRepositoryImpl): TransactionRepository

    @Singleton
    @Binds
    fun bindTransientDataSource(dataSource: TransientDataSourceImpl): TransientDataSource

    @Singleton
    @Binds
    fun bindLocalDataSource(dataSource: LocalDataSourceImpl): LocalDataSource
}

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

    @Singleton
    @Provides
    fun provideTransientDataSource() = TransientDataSourceImpl(LinkedList())
}
