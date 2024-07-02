package com.app.todo.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.app.todo.ui.resources.drawables.DrawableResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoAppTopBar(
    title: String,
    navigateBack: (() -> Unit)? = null
) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(containerColor = MaterialTheme.colorScheme.primaryContainer),
        navigationIcon = {
            if (navigateBack != null) {
                IconButton(onClick = { navigateBack.invoke() }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = DrawableResources.icBaselineArrowBack24),
                        contentDescription = "Back",
                        tint = Color.White,
                    )
                }
            } else null
        }
    )
}
