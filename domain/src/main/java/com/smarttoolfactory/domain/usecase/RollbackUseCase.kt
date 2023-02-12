package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionError
import com.smarttoolfactory.domain.model.TransactionResult
import javax.inject.Inject

class RollbackUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(): TransactionResult<Unit> {
        val transactionStack = repository.getTransactionStack()
        val transactionCount = transactionStack.size
        return if (transactionCount > 1) {
            TransactionResult.Success(
                repository.removeCurrentTransaction()
            )
        } else {
            TransactionResult.Error(TransactionError.NoTransactionError)
        }
    }
}
