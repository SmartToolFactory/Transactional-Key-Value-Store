package com.smarttoolfactory.transactionvaluestore

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.smarttoolfactory.domain.model.Command
import com.smarttoolfactory.domain.model.TransactionError
import com.smarttoolfactory.transactionvaluestore.chat.ChatInput
import com.smarttoolfactory.transactionvaluestore.chat.MessageStatus
import com.smarttoolfactory.transactionvaluestore.chat.ReceivedMessageRow
import com.smarttoolfactory.transactionvaluestore.chat.SentMessageRow
import com.smarttoolfactory.transactionvaluestore.chat.widget.ChatAppbar
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionScreen(
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val enableUi = viewModel.enableUi
    val commandList = viewModel.commandList

    val sdf = remember { SimpleDateFormat("hh:mm", Locale.ROOT) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xffFBE9E7))
    ) {

        ChatAppbar(
            title = "Transaction Store Sample",

            )
        val scrollState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            state = scrollState,
            contentPadding = PaddingValues(top = 8.dp, bottom = 8.dp)
        ) {
            items(commandList) { command: Command ->

                when (command) {
                    is Command.Input -> {
                        val inputText =
                            "> ${command.transaction} " +
                                    "${command.key.orEmpty()} " +
                                    command.value.orEmpty()

                        SentMessageRow(
                            text = inputText,
                            messageTime = sdf.format(System.currentTimeMillis()),
                            messageStatus = MessageStatus.READ
                        )
                    }
                    is Command.Success -> {
                        val successText = command.output
                        ReceivedMessageRow(
                            text = successText,
                            messageTime = sdf.format(System.currentTimeMillis())
                        )
                    }
                    is Command.Error -> {

                        val errorText = when (command.error) {
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
                            TransactionError.InvalidCommandError -> {
                                "Invalid Command"
                            }
                            else -> {
                                "unknown error"
                            }
                        }

                        ReceivedMessageRow(
                            text = errorText,
                            messageTime = sdf.format(System.currentTimeMillis())
                        )

                    }
                    else -> Unit
                }
            }
        }

        Column(modifier = Modifier.padding(8.dp)) {
            ChatInput(
                enabled = enableUi,
                modifier = Modifier.imePadding(),
                onMessageChange = { transaction ->
                    viewModel.submitTransaction(transaction)
                    coroutineScope.launch {
                        scrollState.animateScrollToItem(
                            commandList
                                .lastIndex
                                .coerceAtLeast(0)
                        )
                    }
                }
            )
        }
    }
}