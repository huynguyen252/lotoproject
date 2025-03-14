package com.hnc.company.lototools.presentation.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.text.GPackageBaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.utils.nonRippleClick
import com.hnc.company.lototools.utils.toFormattedDate

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun XSMBResultScreen(
    navController: NavController,
    viewModel: XSMBResultViewModel = hiltViewModel()
) {
    val players = viewModel.result.collectAsStateWithLifecycle().value
    val date = viewModel.date.collectAsStateWithLifecycle().value

    var isShowing by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseTheme.colors.transparentColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BaseTheme.colors.purplePrimary)
                .padding(16.dp)
        ) {
            Text(
                text = "Kết quả XSMB",
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
            )

            Spacer(modifier = Modifier.height(10.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(5.dp)
                    )
                    .background(BaseTheme.colors.purpleDark)
                    .padding(12.dp)
                    .nonRippleClick {
                        isShowing = true
                    }
            ) {
                GPackageBaseText(
                    text = date,
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.Center),
                    color = BaseTheme.colors.textWhite,
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                modifier = Modifier
                    .wrapContentHeight()
            ) {
                itemsIndexed(players?.results?.entries?.toList() ?: emptyList()) { index, entry ->
                    val title = entry.key
                    val numbers = entry.value
                    val isDBRow = (title == "G.DB")

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(15.dp))
                                .background(
                                    Color.White.copy(alpha = 0.1f),
                                    shape = MaterialTheme.shapes.small
                                )
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            GPackageBaseText(
                                text = title,
                                modifier = Modifier.width(50.dp),
                                type = if (isDBRow) TextType.BODY1 else TextType.BODY2,
                                color = if (isDBRow) Color.Red else BaseTheme.colors.textWhite,
                                textAlign = TextAlign.Start,
                                maxLines = 1
                            )
                            FlowRow(
                                modifier = Modifier.weight(1f)
                            ) {
                                numbers.forEach { number ->
                                    GPackageBaseText(
                                        text = number,
                                        modifier = Modifier
                                            .wrapContentHeight()
                                            .padding(end = 10.dp),
                                        type = if (isDBRow) TextType.BODY1 else TextType.BODY2,
                                        color = if (isDBRow) Color.Red else BaseTheme.colors.textWhite,
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                }
            }
        }
    }

    if (isShowing) {
        DatePickerModal(onDateSelected = { result ->
            result?.let {
                viewModel.updateTime(it.toFormattedDate())
            }

        }, onDismiss = { isShowing = false })
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
