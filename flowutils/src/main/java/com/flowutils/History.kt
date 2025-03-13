package com.flowutils

/**
 * Data class representing a history of values.
 *
 * @param previous The previous value.
 * @param current The current value.
 */
data class History<T>(val previous: T?, val current: T)
