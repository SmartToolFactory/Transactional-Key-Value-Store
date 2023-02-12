package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionResult
import javax.inject.Inject

class BeginUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(): TransactionResult.Success<Unit> {
        repository.begin()
        return TransactionResult.Success(Unit)
    }

}