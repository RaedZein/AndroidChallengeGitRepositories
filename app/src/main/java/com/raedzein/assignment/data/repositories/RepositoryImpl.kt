package com.raedzein.assignment.data.repositories

import com.raedzein.assignment.data.api.GitReposService
import com.raedzein.assignment.data.local.ApplicationDatabase
import com.raedzein.assignment.domain.model.FavouritedRepo
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.domain.repositories.Repository
import javax.inject.Inject


class RepositoryImpl @Inject constructor(private val service: GitReposService,
                                         private val database: ApplicationDatabase): Repository {

    override suspend fun getMostStarredReposFromApi(query: Map<String, String>) =
        safeApiResult { service.getGithubRepositories(query) }

    override fun getReposFromDb() = database.githubRepo().allGithubRepos()

    override suspend fun clearAllReposFromDb() = database.githubRepo().deleteAll()

    override suspend fun saveRepositoriesToDb(repos: List<GithubRepo>) = database.githubRepo().insert(repos)
    override suspend fun updateRepo(repo: GithubRepo) = database.githubRepo().insert(repo)

    override fun getFavouritedRepoLiveData(repoId: Long) = database.favouritedRepo().getLiveData(repoId)
    override suspend fun getFavouritedRepos() = database.favouritedRepo().getAll()

    override suspend fun getFavouritedRepo(repoId: Long) = database.favouritedRepo().get(repoId)

    override suspend fun setFavouritedRepo(repoId: Long, favourite: Boolean) {
        if(favourite)
            database.favouritedRepo().insert(FavouritedRepo(repoId))
        else if(getFavouritedRepo(repoId) != null)
            database.favouritedRepo().delete(repoId)
    }
}