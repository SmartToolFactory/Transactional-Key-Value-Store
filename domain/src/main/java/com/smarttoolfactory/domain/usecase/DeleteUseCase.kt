package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(key: String): TransactionResult.Success<Unit> {
        repository.delete(key)
        return TransactionResult.Success(Unit)
    }
}
