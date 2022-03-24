package com.raedzein.assignment.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.raedzein.assignment.data.api.GitReposQueryBuilder
import com.raedzein.assignment.domain.ApiResultState
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.domain.repositories.Repository

@OptIn(ExperimentalPagingApi::class)
class RepositoriesMediator(
  private val queryBuilder: GitReposQueryBuilder,
  private val repository: Repository
) : RemoteMediator<Int, GithubRepo>() {
  private var currentPage = 1
  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, GithubRepo>
  ): MediatorResult {
    currentPage = when (loadType) {
      LoadType.REFRESH ->
        getRefreshKey(state)?: 1

      LoadType.PREPEND ->
        return MediatorResult.Success(endOfPaginationReached = false)

      LoadType.APPEND -> {
        state.lastItemOrNull() ?:
        return MediatorResult.Success(endOfPaginationReached = true)
        currentPage.plus(1)
      }
    }

    return when (val result = repository.getMostStarredReposFromApi(queryBuilder.build(currentPage))) {
      is ApiResultState.Success -> {

        val repos = result.data
        val endOfPaginationReached = repos.isEmpty()
        if (loadType == LoadType.REFRESH)
          repository.clearAllReposFromDb()

        repository.saveRepositoriesToDb(repos)
        MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
      }
      is ApiResultState.Error ->
        MediatorResult.Error(result.exception)
    }
  }
  private fun getRefreshKey(state: PagingState<Int, GithubRepo>): Int? {
    return state.anchorPosition?.let { anchorPosition ->
      val anchorPage = state.closestPageToPosition(anchorPosition)
      anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
    }
  }
}