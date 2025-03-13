package com.ghtk.internal.gflow.thread

import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlin.coroutines.cancellation.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

/**
 * Instance-based utility for running tasks on a worker thread with lifecycle support.
 *
 * Use this class if you need a unique instance for a specific scope or configuration.
 *
 * @param context Optional [Context] to derive a [Lifecycle] if available.
 * @param lifecycle Optional [Lifecycle] to observe for task cancellation.
 */
class GWorkerThreadV2(
    private val context: Context? = null,
    private val lifecycle: Lifecycle? =
        (context?.takeIf { it is LifecycleOwner } as? LifecycleOwner)?.lifecycle,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
) {

    init {
        GWorkerThreadCommon.observeLifecycle(lifecycle, scope)
    }

    /**
     * Executes a [GRunnable] with optional lifecycle-based cancellation.
     *
     * @param T The type of result returned by the task.
     * @param runnable The task to execute.
     * @param onCompleted Callback for result or error handling.
     * @param isRunOnMain Whether the task should run on the main thread. Defaults to false.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     *   Defaults to true.
     */
    @JvmOverloads
    fun <T> doOnWorkerThread(
        runnable: GRunnable<T>,
        onCompleted: OnCompleted<T>?,
        isRunOnMain: Boolean = false,
        isCompleteOnMain: Boolean = true,
    ) {
        GWorkerThreadCommon.executeTask(scope, runnable, onCompleted, isRunOnMain, isCompleteOnMain)
    }

    /**
     * Executes a [Runnable] with optional lifecycle-based cancellation.
     *
     * @param runnable The task to execute.
     * @param onCompleted Callback for result or error handling.
     * @param isRunOnMain Whether the task should run on the main thread. Defaults to false.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     *   Defaults to true.
     */
    @JvmOverloads
    fun doOnWorkerThread(
        runnable: Runnable,
        onCompleted: OnCompleted<Unit>?,
        isRunOnMain: Boolean = false,
        isCompleteOnMain: Boolean = true,
    ) {
        GWorkerThreadCommon.executeTask(scope, runnable, onCompleted, isRunOnMain, isCompleteOnMain)
    }

    /**
     * Cancels all tasks for this instance.
     *
     * @param reason Reason for cancellation. Defaults to "Cancelled by caller."
     */
    fun cancelAllTasks(reason: String = "Cancelled by caller") {
        scope.cancel(CancellationException(reason))
    }
}
