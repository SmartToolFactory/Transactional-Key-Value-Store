@file:OptIn(ExperimentalCoroutinesApi::class)

package com.smarttoolfactory.transactionvaluestore

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarttoolfactory.domain.TransactionManager
import com.smarttoolfactory.domain.model.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionManager: TransactionManager
) : ViewModel() {

    var enableUi by mutableStateOf(false)

    private var query by mutableStateOf<String?>(null)

    fun submitTransaction(transaction: String) {
        query = transaction
    }

    var commandList = mutableStateListOf<Command>()

    init {
        transactionManager
            .init()
            .onEach {
                enableUi = true
            }
            .launchIn(viewModelScope)

        snapshotFlow { query }
            .flatMapLatest { input: String? ->
                input?.let {
                    transactionManager.doTransaction(input)
                } ?: kotlin.run {
                    flow {
                        emit(Command.Complete())
                    }
                }
            }
            .onEach { command: Command ->
                if((command is Command.Complete).not()){
                    commandList.add(command)
                }
            }
            .launchIn(viewModelScope)
    }
}
