package com.smarttoolfactory.domain

import com.smarttoolfactory.domain.model.Command
import com.smarttoolfactory.domain.model.TransactionError
import com.smarttoolfactory.domain.model.TransactionResult
import com.smarttoolfactory.domain.model.TransactionType
import com.smarttoolfactory.domain.usecase.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class TransactionManager @Inject constructor(
    private val beginUseCase: BeginUseCase,
    private val commitUseCase: CommitUseCase,
    private val countUseCase: CountUseCase,
    private val deleteUseCase: DeleteUseCase,
    private val getUseCase: GetUseCase,
    private val initUseCase: InitUseCase,
    private val rollbackUseCase: RollbackUseCase,
    private val setUseCase: SetUseCase
) {


    fun doTransaction(input: String): Flow<Command> {
        return decodeCommand(input)
            .flatMapLatest { command: Command.Input ->
                getTransaction(command)
            }
            .catch {
                emitAll(
                    flowOf(
                        Command.Error(TransactionError.WrongCommandError)
                    )
                )
            }
    }

    private fun decodeCommand(input: String): Flow<Command.Input> {
        return flow {
            val inputList = input.split(" ")
            inputList.firstOrNull()?.uppercase()?.let { transaction: String ->
                emit(
                    Command.Input(
                        transaction = transaction,
                        key = inputList.getOrNull(1),
                        value = inputList.getOrNull(2)
                    )
                )
            } ?: kotlin.run {
                throw NoSuchElementException()
            }
        }
    }

    private fun getTransaction(input: Command.Input): Flow<Command> {
        return flow {

            when (input.transaction) {
                TransactionType.Begin.value -> {
                    emit(Command.Complete(beginUseCase()))
                }
                TransactionType.Get.value -> {
                    getFlow(input)
                }
                TransactionType.Set.value -> {
                    setFlow(input)
                }
                TransactionType.Delete.value -> {
                    deleteFlow(input)
                }
                TransactionType.Count.value -> {
                    countFlow(input)
                }
                TransactionType.Commit.value -> {
                    commitFlow()
                }
                TransactionType.Rollback.value -> {
                    rollbackFlow()
                }
                else -> {
                    emit(
                        Command.Error(
                            error = TransactionError.WrongCommandError
                        )
                    )
                }
            }
        }
            .catch { cause ->
                if (cause.cause is NoSuchElementException) {
                    emit(
                        Command.Error(
                            error = TransactionError.WrongCommandError
                        )
                    )
                } else {
                    emitAll(
                        flowOf(
                            Command.Error(TransactionError.UnknownError)
                        )
                    )
                }
            }
    }

    private suspend fun FlowCollector<Command>.getFlow(
        input: Command.Input
    ) {
        if (input.key.isNullOrEmpty()) {
            emit(Command.Error(TransactionError.NoKeyCommandError))
        } else {
            val result = getUseCase(input.key)
            if (result is TransactionResult.Success) {
                emit(Command.Success(result.data))
            } else {
                emit(Command.Error(TransactionError.KeyNotSetError))
            }
        }
    }

    private suspend fun FlowCollector<Command>.setFlow(input: Command.Input) {
        if (input.key.isNullOrEmpty()) {
            emit(Command.Error(TransactionError.NoKeyCommandError))
        } else if (input.value.isNullOrEmpty()) {
            emit(Command.Error(TransactionError.NoValueCommandError))
        } else {
            emit(Command.Complete(setUseCase(input.key, input.value)))
        }
    }

    private suspend fun FlowCollector<Command>.deleteFlow(input: Command.Input) {
        if (input.key.isNullOrEmpty()) {
            emit(Command.Error(TransactionError.NoKeyCommandError))
        } else {
            emit(Command.Complete(deleteUseCase(input.key)))
        }
    }

    private suspend fun FlowCollector<Command>.countFlow(input: Command.Input) {
        if (input.key.isNullOrEmpty()) {
            emit(Command.Error(TransactionError.NoKeyCommandError))
        } else {
            emit(Command.Success(countUseCase(input.key).toString()))
        }
    }

    private suspend fun FlowCollector<Command>.commitFlow() {
        val result = commitUseCase()
        if (result is TransactionResult.Success) {
            emit(Command.Complete(result))
        } else {
            emit(Command.Error(TransactionError.NoTransactionError))
        }
    }

    private suspend fun FlowCollector<Command>.rollbackFlow() {
        val result = rollbackUseCase()
        if (result is TransactionResult.Success) {
            emit(Command.Complete(result))
        } else {
            emit(Command.Error(TransactionError.NoTransactionError))
        }
    }


    fun init(): Flow<Unit> {
        return flow {
            emit(initUseCase.initTransactions())
        }
    }
}