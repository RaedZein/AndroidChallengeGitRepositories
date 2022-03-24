package com.raedzein.assignment.data.repositories

import com.raedzein.assignment.data.CustomErrorHandler
import com.raedzein.assignment.domain.ApiResultState
import retrofit2.Response

suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>
    ): ApiResultState<T> {
        return try {
            val response = call.invoke()
            when {
                response.isSuccessful -> ApiResultState.Success(response.body()!!)
                response.errorBody() != null -> ApiResultState.Error(CustomErrorHandler(response.errorBody()).getError())
                else -> ApiResultState.Error(Exception(response.raw().message()))
            }

        } catch (exception: Exception) {
            ApiResultState.Error(exception)
        }
    }