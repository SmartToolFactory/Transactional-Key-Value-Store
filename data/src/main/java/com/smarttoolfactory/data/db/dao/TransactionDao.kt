package com.smarttoolfactory.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.smarttoolfactory.data.model.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    /**
     * get list of [TransactionEntity]
     */
    @Query("SELECT * FROM table_transaction")
    suspend fun getTransactionList(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM table_transaction")
    suspend fun getTransactionMap(): Map<String, String> {
        val transactionMap = hashMapOf<String, String>()
        getTransactionList().forEach { transactionEntity: TransactionEntity ->
            transactionMap[transactionEntity.key] = transactionEntity.value
        }

        return transactionMap
    }

    /**
     * Insert a transaction
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(entity: TransactionEntity)

    @Transaction
    suspend fun insertTransactions(map: Map<String, String>) {
        map.forEach { (key, value) ->
            insertTransaction(TransactionEntity(key, value))
        }
    }

    /**
     * Delete [TransactionEntity]
     */
    @Delete
    suspend fun deleteTransaction(entity: TransactionEntity)

    /**
     * Update an existing [TransactionEntity]
     */
    @Update
    suspend fun updateTransaction(entity: TransactionEntity): Int
}
