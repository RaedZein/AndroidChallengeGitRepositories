package com.raedzein.assignment.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.raedzein.assignment.domain.model.GithubRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class RepoDetailsViewModel
@Inject constructor(): ViewModel() {


    private val _githubReposLiveData = MutableLiveData<GithubRepo>()
    val githubReposLiveData: LiveData<GithubRepo> = _githubReposLiveData

    fun setGithubRepoDetails(repo: GithubRepo) {
        _githubReposLiveData.value = repo
    }

}