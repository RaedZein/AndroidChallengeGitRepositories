package com.raedzein.assignment.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.raedzein.assignment.databinding.FragmentListBinding
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.ui.base.ViewBindingFragment
import com.raedzein.assignment.ui.details.RepoDetailsViewModel
import com.raedzein.assignment.ui.list.adapters.RepoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ListFragment : ViewBindingFragment<FragmentListBinding>() {

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentListBinding.inflate(layoutInflater, container, false)


    private val repoListAdapter by lazy {
        RepoListAdapter(::openDetailsPage)
    }

    private val listingViewModel by viewModels<ReposListingViewModel>()
    private val repoDetailsViewModel by activityViewModels<RepoDetailsViewModel>()

    override fun setUpViews() {

        setObservers()
        setUpRecyclerView()

    }

    private fun setUpRecyclerView() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            repoListAdapter.refresh()
        }
        binding.recyclerView.adapter = repoListAdapter

    }
    private fun setObservers() {

        listingViewModel.reposListingLiveData.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                repoListAdapter.submitData(it)
            }
        }
    }

    private fun showListLoading(shouldShow: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = shouldShow
    }



    private fun openDetailsPage(repo: GithubRepo) {
        repoDetailsViewModel.setGithubRepoDetails(repo)
        findNavController().navigate(
            ListFragmentDirections
                .actionListingFragmentToDetailsFragment())
    }

}
