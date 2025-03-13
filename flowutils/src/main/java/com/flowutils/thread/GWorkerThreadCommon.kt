package com.flowutils.thread

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/** Internal utility object containing shared logic for worker thread tasks. */
internal object GWorkerThreadCommon {

    /**
     * Executes a task on a specified thread and manages result or error callbacks.
     *
     * @param T The type of result produced by the task.
     * @param scope The [CoroutineScope] for launching the task.
     * @param runnable The task to be executed.
     * @param onCompleted Callback for handling result or error.
     * @param isRunOnMain Whether the task should run on the main thread.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     */
    fun <T> executeTask(
        scope: CoroutineScope,
        runnable: GRunnable<T>,
        onCompleted: OnCompleted<T>?,
        isRunOnMain: Boolean,
        isCompleteOnMain: Boolean,
    ) {
        val dispatcher = if (isRunOnMain) Dispatchers.Main else Dispatchers.IO
        scope.launch(dispatcher) {
            try {
                val result = runnable.run()
                deliverResult(result, onCompleted, isCompleteOnMain)
            } catch (e: CancellationException) {
                // Handle cancellation exception
            } catch (e: Exception) {
                deliverError(e, onCompleted, isCompleteOnMain)
            }
        }
    }

    /**
     * Executes a task on a specified thread and manages result or error callbacks.
     *
     * @param scope The [CoroutineScope] for launching the task.
     * @param runnable The task to be executed.
     * @param onCompleted Callback for handling result or error.
     * @param isRunOnMain Whether the task should run on the main thread.
     * @param isCompleteOnMain Whether the result callback should be delivered on the main thread.
     */
    fun executeTask(
        scope: CoroutineScope,
        runnable: Runnable,
        onCompleted: OnCompleted<Unit>?,
        isRunOnMain: Boolean,
        isCompleteOnMain: Boolean,
    ) {
        val dispatcher = if (isRunOnMain) Dispatchers.Main else Dispatchers.IO
        scope.launch(dispatcher) {
            try {
                runnable.run()
                deliverResult(Unit, onCompleted, isCompleteOnMain)
            } catch (e: CancellationException) {
                // Handle cancellation exception
            } catch (e: Exception) {
                deliverError(e, onCompleted, isCompleteOnMain)
            }
        }
    }

    /** Delivers the result to `onCompleted` on the specified thread. */
    suspend fun <T> deliverResult(
        result: T,
        onCompleted: OnCompleted<T>?,
        isCompleteOnMain: Boolean,
    ) {
        if (isCompleteOnMain) {
            withContext(Dispatchers.Main) { onCompleted?.onFinish(result) }
        } else {
            onCompleted?.onFinish(result)
        }
    }

    /** Delivers the error to `onCompleted` on the specified thread. */
    suspend fun <T> deliverError(
        e: Exception,
        onCompleted: OnCompleted<T>?,
        isCompleteOnMain: Boolean,
    ) {
        if (isCompleteOnMain) {
            withContext(Dispatchers.Main) { onCompleted?.onError(e.message ?: "Unknown error") }
        } else {
            onCompleted?.onError(e.message ?: "Unknown error")
        }
    }

    /**
     * Observes the lifecycle and cancels the associated [CoroutineScope] when the lifecycle is
     * destroyed.
     *
     * @param lifecycle The [Lifecycle] to observe.
     * @param scope The [CoroutineScope] to cancel when the lifecycle is destroyed.
     */
    fun observeLifecycle(
        lifecycle: Lifecycle?,
        scope: CoroutineScope
    ) {
        lifecycle?.addObserver(
            object : DefaultLifecycleObserver {
                override fun onDestroy(owner: LifecycleOwner) {
                    scope.cancel(
                        CancellationException("Lifecycle destroyed, auto-cancelling tasks")
                    )
                    lifecycle.removeObserver(this)
                }
            }
        )
    }
}
