package com.smarttoolfactory.domain.model

sealed class TransactionResult<out R> {
    data class Success<out T>(val data: T) : TransactionResult<T>()
    data class Error(val transactionError: TransactionError) : TransactionResult<Nothing>()
}