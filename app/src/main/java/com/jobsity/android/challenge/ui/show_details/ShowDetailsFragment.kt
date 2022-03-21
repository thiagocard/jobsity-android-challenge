package com.jobsity.android.challenge.ui.show_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.jobsity.android.challenge.BR
import com.jobsity.android.challenge.MainActivity
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.databinding.FragmentShowDetailsBinding
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowDetailsFragment : Fragment() {

    private val viewModel by viewModel<ShowDetailsViewModel>()
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<ShowDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setShowId(args.showId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.show.collectLatest { state ->
                        when (state) {
                            is ViewState.Error -> {
                                binding.progressBar.isVisible = false
                                Snackbar.make(
                                    binding.root,
                                    state.errorMessage() ?: getString(R.string.generic_error),
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            is ViewState.Loaded -> {
                                (activity as MainActivity).binding.toolbar.title = state.data.name
                                (activity as MainActivity).binding.toolbar.subtitle =
                                    null // clear subtitle
                                binding.progressBar.isVisible = false
                                binding.constraintLayout.isVisible = true
                                binding.setVariable(BR.show, state.data)
                                binding.rvGenres.adapter = GenresAdapter(state.data.genres)
                                (activity as MainActivity).apply {
                                    showFab()
                                    setFabListener { viewModel.addOrRemoveToFavorites() }
                                }
                            }
                            ViewState.Loading -> {
                                binding.progressBar.isVisible = true
                                binding.constraintLayout.isVisible = false
                            }
                        }
                    }
                }

                launch {
                    viewModel. isFavorite.collectLatest { isFavorite ->
                        (activity as MainActivity).changeFabIcon(
                            if (isFavorite == true) R.drawable.ic_baseline_favorite_24
                            else R.drawable.ic_baseline_favorite_border_24
                        )
                    }
                }

                launch {
                    viewModel.episodes.collectLatest { state ->
                        when (state) {
                            is ViewState.Error -> {
                                hideEpisodes()
                                Log.w(
                                    this@ShowDetailsFragment::class.simpleName,
                                    getString(R.string.failed_to_fetch_episodes),
                                    state.throwable
                                )
                            }
                            is ViewState.Loaded -> {
                                binding.rvEpisodes.isNestedScrollingEnabled = false
                                binding.rvEpisodes.adapter =
                                    SeasonsAdapter(state.data.seasons) { episode ->
                                        (activity as MainActivity).navController.navigate(
                                            ShowDetailsFragmentDirections.actionShowDetailsToEpisodeDetails(
                                                episode.id
                                            )
                                        )
                                    }
                                binding.tvEpisodes.isVisible = true
                                binding.rvEpisodes.isVisible = true
                            }
                            ViewState.Loading -> {
                                hideEpisodes()
                            }
                            ViewState.Idle -> {}
                        }
                    }
                }
            }
        }
    }

    private fun hideEpisodes() {
        binding.tvEpisodes.isVisible = false
        binding.rvEpisodes.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideFab()
        _binding = null
    }

}