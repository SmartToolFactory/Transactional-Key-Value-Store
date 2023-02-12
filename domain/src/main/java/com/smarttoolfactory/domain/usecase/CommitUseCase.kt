package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionError
import com.smarttoolfactory.domain.model.TransactionResult
import javax.inject.Inject

class CommitUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(): TransactionResult<Unit> {
        val transactionStack = repository.getTransactionStack()
        val transactionCount = transactionStack.size
        return if (transactionCount > 1) {
            repository.getCurrentTransaction().map.forEach { (key, value) ->
                // Update parent transaction of current transaction before commit
                repository
                    .getTransactionStack()
                    .getOrNull(transactionCount - 2)?.map?.set(key, value)

            }

            repository.clearLocalStore()
            repository.insertAllToToLocalStore(repository.getCurrentTransaction().map)
            repository.removeCurrentTransaction()

            TransactionResult.Success(Unit)
        } else {
            TransactionResult.Error(TransactionError.NoTransactionError)
        }
    }
}
