package com.smarttoolfactory.data

import com.smarttoolfactory.data.model.Transaction


/**
 * Mapper for transforming objects between database and domain
 * which are Non-nullable to Non-nullable
 */
interface Mapper<I, O> {
    /**
     * Maps from [I] type to [O] type
     * This for mapping from input type to output type
     */
    fun mapToOutput(input: I): O

    /**
     * Maps from [O] type to [I] type
     * This for mapping from output type to input type
     */
    fun mapToInput(output: O): I
}

/**
 * Mapper for transforming objects between database and domain
 * as [List] items which are Non-nullable to Non-nullable
 */
interface ListMapper<I, O> : Mapper<List<I>, List<O>>

object TransactionMapper : Mapper<HashMap<String, String>, Transaction> {

    override fun mapToOutput(input: HashMap<String, String>): Transaction {
        return Transaction(input)
    }

    override fun mapToInput(output: Transaction): HashMap<String, String> {
        return output.map
    }
}