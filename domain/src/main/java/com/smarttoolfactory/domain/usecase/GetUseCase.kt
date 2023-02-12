package com.smarttoolfactory.domain.usecase

import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionError
import com.smarttoolfactory.domain.model.TransactionResult
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(key: String): TransactionResult<String> {
        val value = repository.getValue(key)
        value?.let {
            return TransactionResult.Success(it)
        } ?: kotlin.run {
            return TransactionResult.Error(TransactionError.KeyNotSetError)
        }
    }
}
