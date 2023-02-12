package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionResult
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SetUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(key: String, value: String): TransactionResult<Unit> {
        repository.setValue(key, value)
        return TransactionResult.Success(Unit)
    }
}
