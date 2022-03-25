package com.raedzein.assignment.ui.details

import androidx.lifecycle.*
import com.raedzein.assignment.domain.model.FavouritedRepo
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.domain.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RepoDetailsViewModel
@Inject constructor(private val repository: Repository): ViewModel() {


    private val _githubReposLiveData = MutableLiveData<GithubRepo>()
    val githubReposLiveData: LiveData<GithubRepo> = _githubReposLiveData

    fun setGithubRepoDetails(repo: GithubRepo) {
        _githubReposLiveData.value = repo

        //Set the favourite liveData
        favouritedLiveData =
            repository.getFavouritedRepoLiveData(repoId = repo.id).transformToBooleanLiveData()
    }

    private fun LiveData<FavouritedRepo?>.transformToBooleanLiveData() = map { it != null }


    lateinit var favouritedLiveData: LiveData<Boolean>
        private set

    fun setFavourite(repoId: Long, favourite: Boolean) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.setFavouritedRepo(repoId,favourite)
            val repo = _githubReposLiveData.value

            repo?.let {
                it.favourited = favourite
                repository.updateRepo(repo)
            }
        }
    }

}