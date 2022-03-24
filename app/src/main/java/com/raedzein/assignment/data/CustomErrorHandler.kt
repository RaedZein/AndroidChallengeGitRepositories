package com.raedzein.assignment.data

import com.raedzein.assignment.domain.model.GithubErrorResponse
import com.raedzein.assignment.domain.model.GithubErrorResponseJsonAdapter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okhttp3.ResponseBody

/**
 * This class is designed to return an Exception based on a custom Error response from the backend
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
class CustomErrorHandler(private val errorBody: ResponseBody?){

    private val moshi = Moshi.Builder().build()
    private val errorAdaptersTypes: ArrayList<JsonAdapter<out CustomErrorResponse>> =
        arrayListOf(
            //Add every custom Error adapter
            GithubErrorResponseJsonAdapter(moshi)
        )
    private val exception: Exception


    init { exception = Exception(getCustomErrorType()) }

    private fun getCustomErrorType(): String? {
        val errorJson = errorBody?.string()?:""
        var error : CustomErrorResponse? = null
        //will search the list until we find the custom error adapter that correctly serializes the error body
        for (adapter in errorAdaptersTypes){
            error = serializeError(adapter,errorJson)
            if(error!=null)
                break
        }

        return error?.getError()
    }
    private fun <T: CustomErrorResponse> serializeError(adapter: JsonAdapter<T>, errorJson: String): T? =
        adapter.nullSafe().fromJson(errorJson)

    fun getError() = exception
}
abstract class CustomErrorResponse{
    abstract fun getError() : String?
}

