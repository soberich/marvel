package com.example.marvel.convention.utils

import io.reactivex.Observable
import java.util.concurrent.Callable
import java.util.stream.Stream

@Suppress("unused")
object RxStreams {

    @JvmStatic
    fun <T> fromCallable(callable: Callable<Stream<T>>): Observable<T> = Observable.create {
        try {
            callable.call().forEach(it::onNext)
            it.onComplete()
        } catch (t: Throwable) {
            it.onError(t)
        }
    }

    @JvmStatic
    fun <T> fromStream(stream: Stream<T>): Observable<T> = Observable.create {
        try {
            stream.forEach(it::onNext)
            it.onComplete()
        } catch (t: Throwable) {
            it.onError(t)
        }
    }
}
