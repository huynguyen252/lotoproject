package com.ghtk.internal.gflow.thread;

public interface OnCompleted<T> {
    void onFinish(T object);

    default void onError(String error) {
    }

    default void onError(int errorCode, String error) {
    }

}
