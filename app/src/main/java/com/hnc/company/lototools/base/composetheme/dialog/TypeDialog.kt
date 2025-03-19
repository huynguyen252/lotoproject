package com.hnc.company.lototools.base.composetheme.dialog

import com.hnc.company.lototools.R

enum class TypeDialog {
    ERROR,
    CONFIRM,
    SUCCESS
}

fun TypeDialog.getIcon(): Int = when (this) {
    TypeDialog.ERROR -> {
        R.drawable.composeui_oops_icon
    }
    TypeDialog.CONFIRM -> {
        R.drawable.composeui_oops_icon
    }
    TypeDialog.SUCCESS -> {
        R.drawable.composeui_ic_success_message
    }
    else -> {
        R.drawable.composeui_oops_icon
    }
}
