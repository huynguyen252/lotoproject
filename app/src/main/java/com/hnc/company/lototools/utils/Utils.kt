package com.hnc.company.lototools.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.fragment.app.FragmentActivity
import com.hnc.company.lototools.domain.entity.ResultModel
import dagger.hilt.android.internal.managers.FragmentComponentManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.transform
import org.jsoup.Jsoup

fun <T> Flow<Result<T>>.onEachError(action: suspend (Throwable) -> Unit): Flow<T> =
    transform { value ->
        value.onFailure {
            action(it)
        }
        return@transform emit(value)
    }
        .mapNotNull { it.getOrNull() }

fun String?.convertToDouble(): Double {
    if (this.isNullOrEmpty()) return 0.0
    return try {
        this.toDouble()
    } catch (e: Exception) {
        0.0
    }
}

fun String.convertToLong(): Long = try {
    this.toLong()
} catch (e: Exception) {
    0L
}

fun Long?.toLongDefault(default: Long = 0): Long = try {
    this?:default
} catch (e: Exception) {
    0L
}
fun Int?.toIntDefault(default: Int = 0): Int = try {
    this?:default
} catch (e: Exception) {
    0
}

fun String.convertToFloat(): Float = try {
    this.toFloat()
} catch (e: Exception) {
    0f
}

fun String.convertToInt(): Int = try {
    this.toInt()
} catch (e: Exception) {
    0
}

fun Float.roundNumber(): Float {
    return Math.round(this * 100).toFloat() / 100
}

fun parseHtml(html: String): ResultModel {
    // Lists to store numbers and names.
    val numbers = mutableListOf<String>()
    val names = mutableListOf<String>()
    val results = mutableMapOf<String, List<String>>()

    // Parse the HTML using Jsoup.
    val document = Jsoup.parse(html)

    // Select the first table element (similar to $("table:nth-child(1)")).
    val table = document.select("table:nth-child(1)").first()

    table?.let {
        // Extract numbers from "td.v-giai > span".
        it.select("td.v-giai > span").forEach { element ->
            numbers.add(element.text())
        }

        // Extract names from "tr > td:first-child".
        it.select("tr > td:first-child").forEach { element ->
            val text = element.text()
            if (!text.contains("Mã ĐB")) {
                // Replace "Giải" with "G" and remove spaces.
                val processedName = text.replace("Giải", "G").replace(" ", "")
                if (!names.contains(processedName)) {
                    names.add(processedName)
                }
            }
        }
    }

    // Remove the first element of numbers (like numbers.splice(0, 1)).
    if (numbers.isNotEmpty()) {
        numbers.removeAt(0)
    }

    // Helper function to get a number by index or return "Đang cập nhật" if missing.
    fun getNumber(index: Int): String =
        if (index < numbers.size) numbers[index] else "Đang cập nhật"

    // Build the results map based on fixed index ranges.
    if (numbers.isNotEmpty() && names.size >= 8) {
        results[names[0]] = listOf(getNumber(0))
        results[names[1]] = listOf(getNumber(1))
        results[names[2]] = listOf(getNumber(2), getNumber(3))
        results[names[3]] = listOf(getNumber(4), getNumber(5), getNumber(6), getNumber(7), getNumber(8), getNumber(9))
        results[names[4]] = listOf(getNumber(10), getNumber(11), getNumber(12), getNumber(13))
        results[names[5]] = listOf(getNumber(14), getNumber(15), getNumber(16), getNumber(17), getNumber(18), getNumber(19))
        results[names[6]] = listOf(getNumber(20), getNumber(21), getNumber(22))
        results[names[7]] = listOf(getNumber(23), getNumber(24), getNumber(25), getNumber(26))
    }

    // Extract follow time from the assumed element path "div.follow-time > a"
    val followTime = document
        .select("div.follow-time > a")
        .first()
        ?.text() ?: "Đang cập nhật"

    // You might want to include both times in your result.
    // For example, you could update your ResultModel to include a new field for followTime.
    return ResultModel(
        countNumbers = numbers.size,
        time = followTime,
        results = results
    )
}
