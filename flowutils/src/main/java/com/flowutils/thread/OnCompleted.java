package com.flowutils.thread;

public interface OnCompleted<T> {
    void onFinish(T object);

    default void onError(String error) {
    }

    default void onError(int errorCode, String error) {
    }

}
