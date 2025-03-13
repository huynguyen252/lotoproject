package com.flowutils.thread

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

/**
 * Singleton utility for running tasks on a worker thread with optional lifecycle support.
 *
 * Use this for tasks that do not require instance-level configuration or unique lifecycles.
 */
object GWorkerThread {

    /**
     * Executes a [GRunnable] with optional lifecycle-based cancellation.
     *
     * @param T The type of result returned by the task.
     * @param runnable The task to execute.
     * @param onCompleted Callback for result or error handling.
     * @param lifecycle The [Lifecycle] to observe for auto-cancellation.
     * @param isRunOnMain Whether the task should run on the main thread. Defaults to false.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     *   Defaults to true.
     */
    @JvmOverloads
    @JvmStatic
    fun <T> doOnWorkerThread(
        runnable: GRunnable<T>,
        onCompleted: OnCompleted<T>?,
        lifecycle: Lifecycle? = null,
        isRunOnMain: Boolean = false,
        isCompleteOnMain: Boolean = true,
    ) {
        val scope = lifecycle?.coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.IO)
        GWorkerThreadCommon.observeLifecycle(lifecycle, scope)
        GWorkerThreadCommon.executeTask(scope, runnable, onCompleted, isRunOnMain, isCompleteOnMain)
    }

    /**
     * Executes a [Runnable] with optional lifecycle-based cancellation.
     *
     * @param runnable The task to execute.
     * @param onCompleted Callback for result or error handling.
     * @param lifecycle The [Lifecycle] to observe for auto-cancellation.
     * @param isRunOnMain Whether the task should run on the main thread. Defaults to false.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     *   Defaults to true.
     */
    @JvmOverloads
    @JvmStatic
    fun doOnWorkerThread(
        runnable: Runnable,
        onCompleted: OnCompleted<Unit>?,
        lifecycle: Lifecycle? = null,
        isRunOnMain: Boolean = false,
        isCompleteOnMain: Boolean = true,
    ) {
        val scope = lifecycle?.coroutineScope ?: CoroutineScope(SupervisorJob() + Dispatchers.IO)
        GWorkerThreadCommon.observeLifecycle(lifecycle, scope)
        GWorkerThreadCommon.executeTask(scope, runnable, onCompleted, isRunOnMain, isCompleteOnMain)
    }
}
