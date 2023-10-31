package com.example.invasivespeciesdetection;

import org.jetbrains.annotations.NotNull;

import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

abstract class CustomContinuation<T> implements Continuation<T> {

    @Override
    public void resumeWith(@NotNull Object o) {
        if (o instanceof Result.Failure)
            consumeException((((Result.Failure) o).exception));
        else
            consumeResult((T)o);
    }

    @NotNull
    @Override
    public CoroutineContext getContext() {
        return EmptyCoroutineContext.INSTANCE;
    }

    abstract void consumeResult(T result);

    abstract void consumeException(Throwable exception);
}