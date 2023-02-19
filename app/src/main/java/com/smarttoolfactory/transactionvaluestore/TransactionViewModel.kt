package com.smarttoolfactory.transactionvaluestore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smarttoolfactory.domain.TransactionManager
import com.smarttoolfactory.domain.model.Command
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionManager: TransactionManager
) : ViewModel() {

    var enableUi by mutableStateOf(false)
    val commandList = mutableStateListOf<Command>()

    init {
        transactionManager
            .init()
            .onEach {
                enableUi = true
            }
            .launchIn(viewModelScope)
    }

    fun submitTransaction(transaction: String) {
        viewModelScope.launch {
            transactionManager.doTransaction(transaction)
                .collect { command: Command ->
                    if ((command is Command.Complete).not()) {
                        commandList.add(command)
                    }
                }
        }
    }
}
