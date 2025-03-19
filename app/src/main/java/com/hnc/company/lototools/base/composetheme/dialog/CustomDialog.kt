package com.hnc.company.lototools.base.composetheme.dialog

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.hnc.company.lototools.R
import com.hnc.company.lototools.base.composetheme.BaseTheme

@Composable
fun ProgressBarDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(onDismissRequest = {
        openDialogCustom.value = false
    }) {
        BaseTheme {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .background(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shape = RoundedCornerShape(10.dp)
                    )
            ) {
                LoadingIcon()
            }
        }
    }
}

@Composable
fun LoadingIcon() {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing)
        ),
        label = ""
    )
    Image(
        modifier = Modifier
            .rotate(angle)
            .padding(12.dp),
        painter = painterResource(id = R.drawable.ic_loading),
        colorFilter = ColorFilter.tint(BaseTheme.colors.textPrimary),
        contentDescription = null
    )
}

@Composable
fun CustomDialog(
    openDialogCustom: MutableState<Boolean> = mutableStateOf(false)
) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
    }
}

@Composable
fun ShowDialogMessage(
    message: String,
    openDialog: MutableState<Boolean>,
    type: TypeDialog = TypeDialog.SUCCESS,
    onClose: () -> Unit
) {
    if (openDialog.value) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                openDialog.value = false
                onClose.invoke()
            }
        ) {
            val builder = DialogBuilder()
                .addMessage(message)
                .addType(type)
                .addActionRight(ActionDialog(
                    name = "Đóng",
                    action = {
                        openDialog.value = false
                        onClose.invoke()
                    }
                ))
            CustomDialogUI(openDialogCustom = openDialog, builder)
        }
    }
}

@Composable
fun ShowSuccessDialog(
    message: String,
    openDialog: MutableState<Boolean>
) {
    Dialog(
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { openDialog.value = false }
    ) {
        val builder = DialogBuilder().addMessage(message).addType(TypeDialog.SUCCESS)
        CustomDialogUI(openDialogCustom = openDialog, builder)
    }
}

@Composable
fun ShowSuccessDialog(
    message: String,
    openDialog: MutableState<Boolean>,
    onClose: () -> Unit
) {
    if (openDialog.value) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                openDialog.value = false
                onClose.invoke()
            }
        ) {
            val builder = DialogBuilder()
                .addMessage(message)
                .addType(TypeDialog.SUCCESS)
                .addActionRight(ActionDialog(
                    name = "Đóng",
                    action = {
                        openDialog.value = false
                        onClose.invoke()
                    }
                ))
            CustomDialogUI(openDialogCustom = openDialog, builder)
        }
    }
}

// Layout
@Composable
fun CustomDialogUI(
    openDialogCustom: MutableState<Boolean> = mutableStateOf(false),
    builder: DialogBuilder
) {
    BaseTheme {
        Surface(color = BaseTheme.colors.backgroundDialog, shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.95f)
                    .padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = builder.title ?: "Thông báo",
                    style = BaseTheme.typography.title2,
                    color = BaseTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = builder.type.getIcon()),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(110.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = builder.message ?: "",
                    style = BaseTheme.typography.body1Regular,
                    color = BaseTheme.colors.textPrimary
                )
                if (builder.type == TypeDialog.ERROR) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = builder.appInfo ?: "",
                        style = BaseTheme.typography.body1Regular,
                        color = BaseTheme.colors.textPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = builder.deviceInfo ?: "",
                        style = BaseTheme.typography.body1Regular,
                        color = BaseTheme.colors.textPrimary
                    )
                }
                Spacer(modifier = Modifier.height(15.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (builder.type != TypeDialog.ERROR && builder.type != TypeDialog.SUCCESS) {
                        ButtonWithoutOuterPadding(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            onClick = {
                                openDialogCustom.value = false
                                builder.actionLeft?.action?.invoke()
                            },
                            border = BorderStroke(1.dp, BaseTheme.colors.primary),
                            shape = RoundedCornerShape(20)
                        ) {
                            Text(
                                text = builder.actionLeft?.name ?: "Huỷ",
                                style = BaseTheme.typography.large2,
                                color = BaseTheme.colors.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    ButtonWithoutOuterPadding(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        color = BaseTheme.colors.primary,
                        shape = RoundedCornerShape(
                            20
                        ),
                        onClick = {
                            openDialogCustom.value = false
                            builder.actionRight?.action?.invoke()
                        }
                    ) {
                        Text(
                            text = builder.actionRight?.name ?: "Xác nhận",
                            style = BaseTheme.typography.large2,
                            color = BaseTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun BaseDialogNormal(
    action: (Int) -> Unit,
    builder: DialogBuilder
) {
    BaseTheme {
        Surface(color = BaseTheme.colors.backgroundDialog, shape = RoundedCornerShape(10.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.95f)
                    .padding(all = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = builder.title ?: "Thông báo",
                    style = BaseTheme.typography.title2,
                    color = BaseTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = builder.type.getIcon()),
                    contentDescription = null, // decorative
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(90.dp)
                        .fillMaxWidth()
                )

                Text(
                    text = builder.message ?: "",
                    style = BaseTheme.typography.body1Regular,
                    color = BaseTheme.colors.textPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (builder.type != TypeDialog.ERROR) {
                        ButtonWithoutOuterPadding(
                            modifier = Modifier
                                .weight(1f)
                                .height(36.dp),
                            onClick = {
                                action.invoke(1)
                                builder.actionLeft?.action?.invoke()
                            },
                            border = BorderStroke(1.dp, BaseTheme.colors.primary),
                            shape = RoundedCornerShape(20)
                        ) {
                            Text(
                                text = builder.actionLeft?.name ?: "Huỷ",
                                style = BaseTheme.typography.large2,
                                color = BaseTheme.colors.onPrimary
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                    }
                    ButtonWithoutOuterPadding(
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        color = BaseTheme.colors.primary,
                        shape = RoundedCornerShape(
                            20
                        ),
                        onClick = {
                            action.invoke(2)
                            builder.actionRight?.action?.invoke()
                        }
                    ) {
                        Text(
                            text = builder.actionRight?.name ?: "Xác nhận",
                            style = BaseTheme.typography.large2,
                            color = BaseTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonWithoutOuterPadding(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RectangleShape,
    color: Color = Color.Transparent,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier
            .shadow(elevation = elevation, shape = shape, clip = false)
            .then(if (border != null) Modifier.border(border, shape) else Modifier)
            .background(
                color = color,
                shape = shape
            )
            .clip(shape)
            .then(
                Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = LocalIndication.current,
                    enabled = enabled,
                    onClickLabel = null,
                    role = null,
                    onClick = onClick
                )
            ),
        propagateMinConstraints = false,
        contentAlignment = Alignment.Center
    ) {
        content.invoke()
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun MyDialogUIPreview() {
    val builder = DialogBuilder().addMessage("jojo").addType(TypeDialog.SUCCESS)
    CustomDialogUI(openDialogCustom = mutableStateOf(true), builder)
}
