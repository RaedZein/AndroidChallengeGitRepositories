package com.raedzein.assignment.ui.list

import androidx.lifecycle.*
import androidx.paging.*
import com.raedzein.assignment.data.RepositoriesMediator
import com.raedzein.assignment.data.api.MostStarredReposQueryBuilder
import com.raedzein.assignment.domain.repositories.Repository
import com.raedzein.assignment.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class ReposListingViewModel
@Inject constructor(private val repository: Repository,
    //Query builder to be used for api calls (can be injectable in the future - for customized builder in unit tests)
                    queryBuilder: MostStarredReposQueryBuilder = MostStarredReposQueryBuilder()
) : ViewModel() {

    @OptIn(ExperimentalPagingApi::class)
    val reposListingLiveData =
        Pager(
            config = PagingConfig(Constants.DEFAULT_PER_PAGE, enablePlaceholders = false),
            remoteMediator = RepositoriesMediator(queryBuilder,repository),
    ) {
        repository.getReposFromDb()
    }.flow.cachedIn(viewModelScope)
        .asLiveData()



}