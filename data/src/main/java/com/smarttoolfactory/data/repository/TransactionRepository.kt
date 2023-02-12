package com.smarttoolfactory.data.repository

import com.smarttoolfactory.data.datasource.LocalDataSource
import com.smarttoolfactory.data.datasource.TransientDataSource
import com.smarttoolfactory.data.model.Transaction
import javax.inject.Inject

interface TransactionRepository {

    suspend fun removeCurrentTransaction()
    suspend fun getCurrentTransaction(): Transaction
    suspend fun createTransaction(map: HashMap<String, String>)
    fun getTransactionAtIndex(index: Int): Transaction?

    suspend fun setValue(key: String, value: String)
    suspend fun getValue(key: String): String?
    suspend fun begin()
    suspend fun delete(key: String)

    suspend fun commitToLocalStore(map: Map<String, String>)
}

class TransactionRepositoryImpl @Inject constructor(
    private val transientDataSource: TransientDataSource,
    private val localDataSource: LocalDataSource
) : TransactionRepository {

    override suspend fun removeCurrentTransaction() {
        transientDataSource.removeCurrentTransaction()
    }

    override suspend fun getCurrentTransaction(): Transaction {
        return transientDataSource.currentTransaction
    }

    override suspend fun createTransaction(map: HashMap<String, String>) {
        transientDataSource.createTransaction(map)
    }

    override fun getTransactionAtIndex(index: Int): Transaction? {
        return transientDataSource.getTransactionAtIndex(index)
    }

    override suspend fun setValue(key: String, value: String) {
        transientDataSource.setValue(key, value)
    }

    override suspend fun getValue(key: String): String? {
        return transientDataSource.getValue(key)
    }

    override suspend fun begin() {
        val currentStore = transientDataSource
            .currentTransaction
            .map
            .toMutableMap()
        transientDataSource.createTransaction(currentStore)
    }

    override suspend fun delete(key: String) {
        transientDataSource.delete(key)
    }

    override suspend fun commitToLocalStore(map: Map<String, String>) {
        localDataSource.put(map)
    }
}
