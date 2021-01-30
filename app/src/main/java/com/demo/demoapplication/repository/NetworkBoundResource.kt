package com.demo.demoapplication.repository

import com.demo.demoapplication.model.Acronym
import com.demo.demoapplication.room.AcronymEntity
import kotlinx.coroutines.flow.*

inline fun networkBoundResource(
    crossinline query: suspend () -> Flow<List<AcronymEntity>>,
    crossinline fetch: suspend () -> Acronym,
    crossinline saveFetchResult: suspend (Acronym) -> Unit,
    crossinline onFetchFailed: (Throwable) -> Unit = { Unit },
    crossinline shouldFetch: (List<AcronymEntity>) -> Boolean = { true }
) = flow {
    emit(Resource.loading(null))
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.loading(data))
        try {
            saveFetchResult(fetch())
            query().map { Resource.success(it) }
        } catch (throwable: Throwable) {
            onFetchFailed(throwable)
            query().map { Resource.error(throwable.message ?: "", it) }
        }
    } else {
        query().map { Resource.success(it) }
    }
    emitAll(flow)
}