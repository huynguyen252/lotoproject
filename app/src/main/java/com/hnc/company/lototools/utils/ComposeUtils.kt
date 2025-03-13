package com.hnc.company.lototools.utils

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Modifier.rippleClick(
    periodMillis: Long = 500L,
    onClick: () -> Unit
): Modifier {
    val multipleEventsCutter = remember { MultipleEventsCutter(periodMillis) }
    return this.combinedClickable(
        onClick = {
            multipleEventsCutter.processEvent {
                onClick.invoke()
            }
        },
        indication = ripple(),
        interactionSource = remember { MutableInteractionSource() },
    )
}

@Composable
fun Modifier.nonRippleClick(
    periodMillis: Long = 500L,
    onClick: () -> Unit
): Modifier {
    val multipleEventsCutter = remember { MultipleEventsCutter(periodMillis) }
    return this.clickable(
        interactionSource = remember { MutableInteractionSource() },
        indication = null,
        onClick = {
            multipleEventsCutter.processEvent {
                onClick.invoke()
            }
        }
    )
}

@Composable
fun rememberLifecycleEvent(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
): Lifecycle.Event {
    var state by remember { mutableStateOf(Lifecycle.Event.ON_ANY) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            state = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    return state
}

@Composable
fun <T> T.useDebounce(
    delayMillis: Long = 1500L,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onChange: (T) -> Unit
): T {
    val state by rememberUpdatedState(this)

    DisposableEffect(state) {
        val job = coroutineScope.launch {
            delay(delayMillis)
            onChange(state)
        }
        onDispose {
            job.cancel()
        }
    }
    return state
}

private class MultipleEventsCutter(private val periodMillis: Long = 500L) {
    private val now: Long
        get() = System.currentTimeMillis()

    private var lastEventTimeMs: Long = 0

    fun processEvent(event: () -> Unit) {
        if (now - lastEventTimeMs >= periodMillis) {
            event.invoke()
        }
        lastEventTimeMs = now
    }
}
