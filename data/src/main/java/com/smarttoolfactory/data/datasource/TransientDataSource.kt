package com.smarttoolfactory.data.datasource

import com.smarttoolfactory.data.model.Transaction
import java.util.LinkedList
import javax.inject.Inject

/**
 * Data source that creates, updates, deletes or counts number
 * of transaction or key-value pairs inside any transaction.
 *
 * This storage is transient which every transaction is deleted when session
 * is finished without committing to a persistent storage
 */
interface TransientDataSource {

    val currentTransaction: Transaction
    val transactionSize: Int

    fun getTransactionStack(): List<Transaction>
    fun getTransactionAtIndex(index: Int): Transaction?
     fun createTransaction(map: MutableMap<String, String>)

     fun getValue(key: String): String?
     fun setValue(key: String, value: String)
     fun removeCurrentTransaction()
     fun delete(key: String)
}

class TransientDataSourceImpl @Inject constructor(
    private val transactionStack: LinkedList<Transaction>
) : TransientDataSource {

    override val currentTransaction: Transaction
        get() = transactionStack.last

    override val transactionSize: Int
        get() = transactionStack.size

    override fun getTransactionAtIndex(index: Int): Transaction? {
        return try {
            transactionStack[index]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    override fun getTransactionStack(): List<Transaction> {
        return transactionStack
    }

    override  fun createTransaction(map: MutableMap<String, String>) {
        transactionStack.add(Transaction(map))
    }

    override  fun getValue(key: String): String? {
        return currentTransaction.map[key]
    }

    override  fun setValue(key: String, value: String) {
        currentTransaction.map[key] = value
    }

    override  fun removeCurrentTransaction() {
        transactionStack.removeLast()
    }

    override  fun delete(key: String) {
        currentTransaction.map.remove(key)
    }
}