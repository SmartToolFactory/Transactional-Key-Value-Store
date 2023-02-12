package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class InitUseCase @Inject constructor(
    internal val repository: TransactionRepository
) {

    suspend fun initTransactions() {
        if (repository.getTransactionStack().isEmpty()) {
            val transactionMap = repository.getLocalStoreTransactions()
            repository.createTransaction(transactionMap.toMutableMap())
        }
    }
}