@file:OptIn(ExperimentalComposeUiApi::class)

package com.smarttoolfactory.transactionvaluestore

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smarttoolfactory.domain.model.Command
import com.smarttoolfactory.domain.model.TransactionError

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        val enableUi = viewModel.enableUi
        var query by remember {
            mutableStateOf("")
        }

        val keyboardController = LocalSoftwareKeyboardController.current

        val commandList = viewModel.commandList

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {

            items(items = commandList) { command: Command ->

                val text = when (command) {
                    is Command.Input -> "> ${command.transaction} ${command.key.orEmpty()} ${command.value.orEmpty()}"
                    is Command.Success -> command.output
                    is Command.Error -> {
                        when (command.error) {
                            TransactionError.EmptyCommandError -> {
                                "Empty Command"
                            }
                            TransactionError.NoKeyCommandError -> {
                                "no key entered"
                            }
                            TransactionError.NoValueCommandError -> {
                                "no value entered"
                            }
                            TransactionError.NoTransactionError -> {
                                "no Transaction"
                            }
                            TransactionError.KeyNotSetError -> {
                                "key not set"
                            }
                            TransactionError.InvalidCommandError-> {
                                "Invalid Command"
                            }
                            else -> {
                                "unknown error"
                            }
                        }
                    }
                    else -> "Complete"
                }

                Text(text = text)
            }
        }

        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(
                modifier = Modifier.weight(1f),
                value = query,
                onValueChange = {
                    query = it
                }
            )

            Button(
                enabled = enableUi,
                onClick = {
                    viewModel.submitTransaction(query)
                    query = ""
                    keyboardController?.hide()
                }) {
                Text(text = "GO")
            }
        }
    }
}