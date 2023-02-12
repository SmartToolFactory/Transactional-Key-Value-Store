package com.smarttoolfactory.domain.model

enum class TransactionError {
    EmptyCommandError,
    NoKeyCommandError,
    NoValueCommandError,
    NoTransactionError,
    KeyNotSetError,
    UnknownError
}