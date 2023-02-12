package com.smarttoolfactory.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "table_transaction")
data class TransactionEntity(
    @PrimaryKey
    val noteId: String = UUID.randomUUID().toString(),
    val key: String,
    val value: String
)