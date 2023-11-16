package com.compose.demo.widget

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 *
 * @author zrh
 * @date 2023/11/15
 *
 */
@Composable
fun Space(width: Dp = 0.dp, height: Dp = 0.dp) {
    Spacer(modifier = Modifier.width(width).height(height))
}

inline fun Modifier.onClick(
    debounceInterval: Long = 400,
    ripple: Boolean = false,
    crossinline onClick: () -> Unit,
): Modifier = composed {
    var lastClickTime by rememberStateOf(value = 0L)
    val indication = if (ripple) LocalIndication.current else null
    clickable(indication = indication,
              interactionSource = remember { MutableInteractionSource() }) {
        val currentTime = System.currentTimeMillis()
        if ((currentTime - lastClickTime) < debounceInterval) return@clickable
        lastClickTime = currentTime
        onClick()
    }
}

@Composable
fun <T> rememberStateOf(value: T): MutableState<T> {
    return remember { mutableStateOf(value) }
}