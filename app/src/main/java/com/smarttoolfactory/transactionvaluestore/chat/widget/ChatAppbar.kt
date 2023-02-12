package com.smarttoolfactory.transactionvaluestore.chat.widget

import android.content.res.Configuration
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChatAppbar(
    title: String = "Title",
) {
    TopAppBar(
        elevation = 4.dp,
        backgroundColor = Color(0xff00897B),
        contentColor = Color.White
    ) {
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }


}

@Preview
@Preview("dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(device = Devices.PIXEL_C)
@Composable
private fun ChatAppbarPreview() {
    ChatAppbar()
}
