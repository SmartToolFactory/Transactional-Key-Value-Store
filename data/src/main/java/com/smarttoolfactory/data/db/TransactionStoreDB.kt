package com.smarttoolfactory.data.db

import androidx.room.*
import com.smarttoolfactory.data.db.dao.TransactionDao
import com.smarttoolfactory.data.model.TransactionEntity

@Database(
    entities = [
        TransactionEntity::class
    ],
    version = 1,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
}
