package com.app.todo.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.app.todo.ui.resources.strings.StringResources

@Composable
fun ErrorAlertDialog(
    message: String,
    okButtonClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = {
        },
        // below line is use to display title of our dialog
        // box and we are setting text color to white.
        title = {
            Text(
                text = stringResource(StringResources.alterTitle),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        },
        text = {
            Text(text = message, fontSize = 16.sp)
        },
        confirmButton = { },
        dismissButton = {
            TextButton(
                onClick = {
                    okButtonClick()
                }) {
                Text(
                    stringResource(StringResources.labelOkay),
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(color = Color.Black)
                )
            }
        },
    )
}
