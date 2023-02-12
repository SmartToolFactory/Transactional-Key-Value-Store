package com.smarttoolfactory.domain.usecase


import com.smarttoolfactory.data.repository.TransactionRepository
import com.smarttoolfactory.domain.model.TransactionResult
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CountUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(value: String): TransactionResult.Success<Int> {
        val count = repository.getCurrentTransaction().map.count {
            it.value == value
        }
        return TransactionResult.Success(count)
    }
}
