package com.raedzein.assignment.data.api

import com.raedzein.assignment.domain.model.GithubRepo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap


interface GitReposService {

    @GET("orgs/square/repos")
    suspend fun getGithubRepositories(@QueryMap queryMap: Map<String, String>): Response<List<GithubRepo>>

}
