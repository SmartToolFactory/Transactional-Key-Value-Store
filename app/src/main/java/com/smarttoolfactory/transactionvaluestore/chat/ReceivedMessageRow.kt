package com.smarttoolfactory.transactionvaluestore.chat


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.smarttoolfactory.transactionvaluestore.chat.widget.ChatFlexBoxLayout

@Composable
fun ReceivedMessageRow(
    text: String,
    messageTime: String,
) {
    // Whole column that contains chat bubble and padding on start or end
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 60.dp, top = 2.dp, bottom = 2.dp)

    ) {

        ChatFlexBoxLayout(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(start = 2.dp,  end = 4.dp),
            text = text,
            messageStat = {
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        modifier = Modifier.padding(top = 1.dp, bottom = 1.dp, end = 4.dp),
                        text = messageTime,
                        fontSize = 12.sp
                    )
                }
            }
        )
    }
}