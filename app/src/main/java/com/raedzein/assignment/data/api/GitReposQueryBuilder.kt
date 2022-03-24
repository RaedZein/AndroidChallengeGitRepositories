package com.raedzein.assignment.data.api

abstract class GitReposQueryBuilder {

    fun build(page: Int): Map<String, String> {
        val queryParams = hashMapOf("page" to page.toString())
        putQueryParams(queryParams)
        return queryParams
    }

    abstract fun putQueryParams(queryParams: MutableMap<String, String>)

}
