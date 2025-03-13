package com.hnc.company.lototools.base.mvi

import android.net.Uri
import android.os.Bundle

interface MviEffect
data class NavigateTo(
    val destination: String? = null,
    val deeplink: Uri? = null,
    val isSingleTop: Boolean = false,
    val bundle: Bundle? = null,
    val popBackStack: Boolean = false
) : MviEffect

object PopBackStack : MviEffect

object NavigateUp : MviIntent
