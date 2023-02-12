package com.smarttoolfactory.domain.model

sealed class Command {
    data class Input(val transaction: String, val key: String?, val value: String?) : Command()
    data class Success(val output: String) : Command()
    data class Error(val error: TransactionError) : Command()
    data class Complete(val complete: Any=Any()) : Command()
}

/*
SET <key1> <value1> <key2> <value2> // store the value for key
GET <key>         // return the current value for key
DELETE <key1> <key2>      // remove the entry for key
COUNT <value>     // return the number of keys that have the given value
BEGIN             // start a new transaction
COMMIT            // complete the current transaction
ROLLBACK          // revert to state prior to BEGIN call
 */
enum class TransactionType(val value: String) {
    Set("SET"),
    Get("GET"),
    Delete("DELETE"),
    Count("COUNT"),
    Begin("BEGIN"),
    Commit("COMMIT"),
    Rollback("ROLLBACK")
}