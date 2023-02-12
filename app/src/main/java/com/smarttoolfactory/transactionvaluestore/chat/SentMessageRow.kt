package com.smarttoolfactory.transactionvaluestore.chat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.smarttoolfactory.transactionvaluestore.chat.widget.ChatFlexBoxLayout


@Composable
fun SentMessageRow(
    text: String,
    messageTime: String,
    messageStatus: MessageStatus
) {

    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 60.dp, end = 8.dp, top = 2.dp, bottom = 2.dp)

    ) {

        ChatFlexBoxLayout(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xffE7FFDB))
                .padding(
                start = 2.dp,
                top = 2.dp,
                end = 4.dp,
                bottom = 2.dp
            ),
            text = text,
            messageStat = {
                MessageTimeText(
                    modifier = Modifier.wrapContentSize(),
                    messageTime = messageTime,
                    messageStatus = messageStatus
                )
            }
        )
    }
}