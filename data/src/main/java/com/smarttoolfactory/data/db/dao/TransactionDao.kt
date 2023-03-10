package com.smarttoolfactory.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.smarttoolfactory.data.model.TransactionEntity

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
     * Delete every transaction in [TransactionEntity] table
     */
    @Query("DELETE FROM table_transaction")
    suspend fun deleteAll()
}
