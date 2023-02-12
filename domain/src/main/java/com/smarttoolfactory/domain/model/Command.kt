package com.smarttoolfactory.domain.model

data class CommandInput(
    val input:String,
    val type:CommandType
)

/*
SET <key1> <value1> <key2> <value2> // store the value for key
GET <key>         // return the current value for key
DELETE <key1> <key2>      // remove the entry for key
COUNT <value>     // return the number of keys that have the given value
BEGIN             // start a new transaction
COMMIT            // complete the current transaction
ROLLBACK          // revert to state prior to BEGIN call
 */
enum class CommandType {
    Set,
    Get,
    Delete,
    Count,
    Begin,
    Commit,
    Rollback
}