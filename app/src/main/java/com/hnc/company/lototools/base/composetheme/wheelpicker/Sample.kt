package com.hnc.company.lototools.base.composetheme.wheelpicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hnc.company.lototools.base.composetheme.BaseTheme

@Preview(showBackground = true)
@Composable
private fun MainView() {
    BaseTheme {
        Row(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Cyan)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            SampleCustomItemSize()
            SampleCustomItemSize()
        }
    }
}

@Composable
private fun RowScope.SampleCustomItemSize() {
    FVerticalWheelPicker(
        modifier = Modifier.weight(1f).background(Color.Green),
        count = 50,
        // Specified item height.
        itemHeight = 40.dp,
        unfocusedCount = 2,
        focus = {
            // Custom divider.
            FWheelPickerFocusVertical()
        }
    ) {
        Text("index item $it")
    }
}
