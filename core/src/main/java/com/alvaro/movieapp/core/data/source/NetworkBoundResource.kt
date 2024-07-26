package com.alvaro.movieapp.core.data.source

import com.alvaro.movieapp.core.presentation.state.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


abstract class NetworkBoundResource<RequestType, ResultType> {
    private var result: Flow<Resource<ResultType>> = flow {
        emit(Resource.Loading())
        val dbSource = loadFromDB().first()
        if (shouldFetch(dbSource)) {
            emit(Resource.Loading())
            when (val apiResponse = createCall().first()) {
                is Resource.Success -> {
                    val data = apiResponse.data
                    saveCallResult(data)
                    if (useDb()) {
                        emitAll(loadFromDB().map { Resource.Success(it) })
                    } else {
                        emit(Resource.Success<ResultType>(data.mapResponseToDomain()))
                    }
                }

                is Resource.Error -> {
                    onFetchFailed()
                    emit(Resource.Error<ResultType>(apiResponse.error))
                }

                else -> Unit
            }
        } else {
            emitAll(loadFromDB().map { Resource.Success(it) })
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): Flow<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract suspend fun createCall(): Flow<Resource<RequestType>>

    protected abstract suspend fun saveCallResult(data: RequestType)

    protected abstract fun RequestType.mapResponseToDomain(): ResultType

    protected abstract fun useDb(): Boolean

    fun asFlow(): Flow<Resource<ResultType>> = result
}