package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.datasource.LocalDataSource
import com.smarttoolfactory.data.datasource.TransientDataSource
import com.smarttoolfactory.data.model.Transaction
import com.smarttoolfactory.data.model.TransactionEntity
import javax.inject.Inject

interface TransactionRepository {

    fun getTransactionStack(): List<Transaction>
    fun removeCurrentTransaction()
    fun getCurrentTransaction(): Transaction
    fun createTransaction(map: MutableMap<String, String>)
    fun getTransactionAtIndex(index: Int): Transaction?

    fun setValue(key: String, value: String)
    fun getValue(key: String): String?
    fun begin()
    fun delete(key: String)

    // Persistent Storage Functions
    suspend fun clearLocalStore()
    suspend fun getLocalStoreTransactions(): Map<String, String>
    suspend fun commitToLocalStore(map: Map<String, String>)
    suspend fun insertTransactionToToLocalStore(transactionEntity: TransactionEntity)
    suspend fun insertAllToToLocalStore(map: Map<String, String>)
}

class TransactionRepositoryImpl @Inject constructor(
    private val transientDataSource: TransientDataSource,
    private val localDataSource: LocalDataSource
) : TransactionRepository {
    override fun getTransactionStack(): List<Transaction> {
        return transientDataSource.getTransactionStack()
    }

    override fun removeCurrentTransaction() {
        transientDataSource.removeCurrentTransaction()
    }

    override fun getCurrentTransaction(): Transaction {
        return transientDataSource.currentTransaction
    }

    override fun createTransaction(map: MutableMap<String, String>) {
        transientDataSource.createTransaction(map)
    }

    override fun getTransactionAtIndex(index: Int): Transaction? {
        return transientDataSource.getTransactionAtIndex(index)
    }

    override fun setValue(key: String, value: String) {
        transientDataSource.setValue(key, value)
    }

    override fun getValue(key: String): String? {
        return transientDataSource.getValue(key)
    }

    override fun begin() {
        val currentStore = transientDataSource
            .currentTransaction
            .map
            .toMutableMap()
        createTransaction(currentStore)
    }

    override fun delete(key: String) {
        transientDataSource.delete(key)
    }

    override suspend fun clearLocalStore() {
        localDataSource.getTransactionMap()
    }

    override suspend fun getLocalStoreTransactions(): Map<String, String> {
        return localDataSource.getTransactionMap()
    }

    override suspend fun commitToLocalStore(map: Map<String, String>) {
        localDataSource.put(map)
    }

    override suspend fun insertTransactionToToLocalStore(transactionEntity: TransactionEntity) {
        localDataSource.put(transactionEntity)
    }

    override suspend fun insertAllToToLocalStore(map: Map<String, String>) {
        localDataSource.put(map)
    }
}
