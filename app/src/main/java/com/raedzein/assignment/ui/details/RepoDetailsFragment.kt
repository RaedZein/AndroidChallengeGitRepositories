package com.raedzein.assignment.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.raedzein.assignment.R
import com.raedzein.assignment.databinding.FragmentRepoDetailsBinding
import com.raedzein.assignment.domain.model.GithubRepo
import com.raedzein.assignment.ui.base.ViewBindingFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RepoDetailsFragment : ViewBindingFragment<FragmentRepoDetailsBinding>() {

    private val repoDetailsViewModel by activityViewModels<RepoDetailsViewModel>()

    override fun getBinding(layoutInflater: LayoutInflater, container: ViewGroup?) =
        FragmentRepoDetailsBinding.inflate(layoutInflater, container, false)

    override fun setUpViews() {
        repoDetailsViewModel.githubReposLiveData.observe(viewLifecycleOwner,::showRepoDetails)
    }

    private fun showRepoDetails(repo: GithubRepo) {

        binding.toolbar.title = repo.name
        binding.textViewOwnerName.text = repo.owner.username
        binding.textViewDescription.text = repo.description

        Glide.with(requireContext())
            .load(repo.owner.avatarUrl)
            .placeholder(R.color.colorPrimaryDark)
            .circleCrop()
            .into(binding.imageViewOwner)

    }

}
