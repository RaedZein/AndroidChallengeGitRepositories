package com.raedzein.assignment.domain.repositories

import androidx.paging.PagingSource
import com.raedzein.assignment.domain.ApiResultState
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.domain.model.GithubRepoList


interface Repository {

    suspend fun getMostStarredReposFromApi(query: Map<String, String>): ApiResultState<List<GithubRepo>>

    fun getReposFromDb(): PagingSource<Int, GithubRepo>
    suspend fun clearAllReposFromDb()
    suspend fun saveRepositoriesToDb(repos: List<GithubRepo>)

}