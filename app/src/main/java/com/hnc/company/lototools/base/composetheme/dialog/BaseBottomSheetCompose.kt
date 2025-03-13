package com.hnc.company.lototools.base.composetheme.dialog

import android.view.View
import android.view.WindowManager
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.view.WindowCompat
import com.hnc.company.lototools.utils.nonRippleClick
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun BaseBottomSheetCompose(
    onDismissSheet: () -> Unit = {},
    swipeToDismiss: Boolean = true,
    clickOutsideDismiss: Boolean = true,
    content: @Composable () -> Unit
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(
            decorFitsSystemWindows = false
        ),
        content = {
            var isAnimateLayout by remember {
                mutableStateOf(false)
            }

            val activityWindow = getActivityWindow()
            val dialogWindow = getDialogWindow()
            val parentView = LocalView.current.parent as View
            // handle width, height
            val displayWidth =
                activityWindow?.decorView?.width ?: LocalContext.current.getDisplayWidth()
            val displayHeight =
                activityWindow?.decorView?.height ?: LocalContext.current.getDisplayHeight()
            SideEffect {
                if (dialogWindow != null) {
                    val attributes = WindowManager.LayoutParams()
                    if (activityWindow != null) { // 判空忽略预览模式
                        attributes.copyFrom(activityWindow.attributes)
                    }
                    attributes.type = dialogWindow.attributes.type
                    dialogWindow.attributes = attributes
                    // 修复Android10 - Android11出现背景全黑的情况
                    dialogWindow.setBackgroundDrawableResource(android.R.color.transparent)
                    // 禁止Dialog跟随软键盘高度变化，用Compose提供的imePadding替代
                    dialogWindow.setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING
                    )

                    dialogWindow.setLayout(displayWidth, displayHeight)
                    dialogWindow.statusBarColor = Color.Transparent.toArgb()
                    dialogWindow.navigationBarColor = Color.Transparent.toArgb()

                    WindowCompat.getInsetsController(dialogWindow, parentView)
                        .isAppearanceLightNavigationBars = true

                    isAnimateLayout = true
                }
            }

            val animColor = remember { Animatable(Color.Transparent) }
            LaunchedEffect(isAnimateLayout) {
                animColor.animateTo(
                    if (isAnimateLayout) Color.Black.copy(alpha = 0.45F) else Color.Transparent,
                    animationSpec = tween(250)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(animColor.value)
                    .nonRippleClick(
                        onClick = {
                            if (clickOutsideDismiss) {
                                onDismissSheet.invoke()
                            }
                        }
                    ),
                contentAlignment = Alignment.BottomCenter
            ) {
                AnimatedVisibility(
                    visible = isAnimateLayout,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = fadeOut() + slideOutVertically(targetOffsetY = { it })
                ) {
                    val offsetY = remember { androidx.compose.animation.core.Animatable(0f) }
                    val scope = rememberCoroutineScope()

                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(color = Color.Transparent)
                            .offset { IntOffset(0, offsetY.value.roundToInt()) }
                            .pointerInput(swipeToDismiss) {
                                if (swipeToDismiss) {
                                    detectVerticalDragGestures(
                                        onVerticalDrag = { change, dragAmount ->
                                            change.consume() // Consume touch event
                                            scope.launch {
                                                val newOffset = (offsetY.value + dragAmount).coerceAtLeast(
                                                    0f
                                                ) // Prevent swipe up
                                                offsetY.snapTo(newOffset)
                                            }
                                        },
                                        onDragEnd = {
                                            scope.launch {
                                                if (offsetY.value > 300) { // Threshold for dismiss
                                                    onDismissSheet()
                                                } else {
                                                    offsetY.animateTo(
                                                        0f,
                                                        animationSpec = tween(200)
                                                    ) // Reset position
                                                }
                                            }
                                        }
                                    )
                                }
                            }
                            .nonRippleClick(onClick = { /* fix click content bi dismiss */ })
                    ) {
                        content()
                    }
                }
            }
            BackHandler(enabled = true, onBack = onDismissSheet)
        }
    )
}
