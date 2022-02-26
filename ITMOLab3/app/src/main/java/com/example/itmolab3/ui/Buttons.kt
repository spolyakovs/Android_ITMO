package com.example.itmolab3.ui

import android.content.res.Resources
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.itmolab3.R
import androidx.compose.runtime.Composable as Composable

val textSecondaryColor = Color(0xFF000000)

@Composable
fun Buttons(clickLike: () -> Unit = {}, clickDislike: () -> Unit = {}) {

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        IconButton(
            onClick = { clickDislike() },
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_dislike),
                    modifier = Modifier.size(35.dp),
                    tint = textSecondaryColor,
                    contentDescription = null
                )
            }
        )
        IconButton(
            onClick = { clickLike() },
            modifier = Modifier.wrapContentWidth().fillMaxHeight(),
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_like),
                    modifier = Modifier.size(35.dp),
                    tint = textSecondaryColor,
                    contentDescription = null
                )
            }
        )
    }
}