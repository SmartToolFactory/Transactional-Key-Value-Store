package com.smarttoolfactory.data.datasource

import com.smarttoolfactory.data.db.dao.TransactionDao
import com.smarttoolfactory.data.model.Transaction
import com.smarttoolfactory.data.model.TransactionEntity
import javax.inject.Inject

/**
 * DataSource that commits current [Transaction] into database or
 * fetches transaction from previous session
 */
interface LocalDataSource {
    suspend fun clear()
    suspend fun put(transactionEntity: TransactionEntity)
    suspend fun put(transactionMap: Map<String, String>)
    suspend fun getTransactionMap(): Map<String, String>
}

class LocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : LocalDataSource {
    override suspend fun clear() {
        transactionDao.deleteAll()
    }

    override suspend fun put(transactionEntity: TransactionEntity) {
        transactionDao.insertTransaction(transactionEntity)
    }

    override suspend fun put(transactionMap: Map<String, String>) {
        transactionDao.insertTransactions(transactionMap)
    }

    override suspend fun getTransactionMap(): Map<String, String> {
        return transactionDao.getTransactionMap()
    }
}
