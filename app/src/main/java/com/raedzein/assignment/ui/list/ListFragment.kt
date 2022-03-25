package com.raedzein.assignment.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.raedzein.assignment.R
import com.raedzein.assignment.databinding.FragmentListBinding
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.ui.base.ViewBindingFragment
import com.raedzein.assignment.ui.details.RepoDetailsViewModel
import com.raedzein.assignment.ui.list.adapters.RepoListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
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
            binding.swipeRefreshLayout.isRefreshing = false
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
        repoListAdapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner){loadStates->
            showListLoading(shouldShow = loadStates.refresh is LoadState.Loading)

            when (loadStates.refresh) {
                is LoadState.Error -> {
                    val error = (loadStates.refresh as LoadState.Error).error
                    showMessageDialog(
                        message = error.message?:"",
                        confirmButtonText = getString(R.string.listing_button_retry),
                        confirmAction = repoListAdapter::refresh)
                }
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

    private fun showMessageDialog(
        message: String,
        confirmButtonText: String,
        title: String? = null,
        cancellable: Boolean = false,
        confirmAction: (() -> Unit)? = null,
        negativeButtonText: String? = null,
        negativeAction: (() -> Unit)? = null
    ) {
        var builder = MaterialAlertDialogBuilder(requireContext())
        if(title != null)
            builder = builder.setTitle(title)
        if(negativeButtonText != null)
            builder = builder
                .setNegativeButton(negativeButtonText) { _, _ ->
                    negativeAction?.invoke()
                }
        builder.setMessage(message)
            .setCancelable(cancellable)
            .setPositiveButton(confirmButtonText) { _, _ ->
                confirmAction?.invoke()
            }
            .create().show()
    }

}
