package com.example.recipebook.core

import androidx.annotation.StringRes
import com.example.recipebook.R
import java.io.IOException

interface HandleError<T> {

    fun handle(error: Exception): T

    class DataToDomain : HandleError<Exception> {

        override fun handle(error: Exception): Exception {
            return when (error) {
                is IOException -> NoInternetConnectionException()
                is CloudDataSourceException -> BackendException(error.message)
                else -> LocalDataSourceException(error.message)
            }
        }
    }

    class DomainToUi() : HandleError<HandleErrorState> {

        override fun handle(error: Exception): HandleErrorState {
            return HandleErrorState(
                message = error.message ?: "",
                stringRes = when (error) {
                    is NoInternetConnectionException -> R.string.internet_connection_failed
                    is BackendException -> R.string.unknown_backend_exception
                    is LocalDataSourceException -> R.string.unknown_local_db_exception
                    else -> R.string.service_unavailable
                }
            )
        }
    }
}

data class HandleErrorState(
    val message: String = "",
    @param:StringRes val stringRes: Int = R.string.internet_connection_failed
)