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
                                    state.errorMessage() ?: "Error",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            is ViewState.Loaded -> {
                                (activity as MainActivity).binding.toolbar.title = state.data.name
                                (activity as MainActivity).binding.toolbar.subtitle = null // clear subtitle
                                binding.progressBar.isVisible = false
                                binding.constraintLayout.isVisible = true
                                binding.setVariable(BR.show, state.data)
                                binding.rvGenres.adapter = GenresAdapter(state.data.genres)
                            }
                            ViewState.Loading -> {
                                binding.progressBar.isVisible = true
                                binding.constraintLayout.isVisible = false
                            }
                        }
                    }
                }

                launch {
                    viewModel.episodes.collectLatest { state ->
                        when (state) {
                            is ViewState.Error -> {
                                binding.groupEpisodes.isVisible = false
                                Log.w(
                                    this@ShowDetailsFragment::class.simpleName,
                                    "Failed to fetch episodes of show",
                                    state.throwable
                                )
                            }
                            is ViewState.Loaded -> {
                                binding.rvEpisodes.isNestedScrollingEnabled = false
                                binding.rvEpisodes.adapter =
                                    EpisodesOfShowAdapter(state.data.episodes) {
                                        (activity as MainActivity).navController.navigate(
                                            ShowDetailsFragmentDirections.actionShowDetailsToEpisodeDetails(
                                                it.id
                                            )
                                        )
                                    }
                                binding.groupEpisodes.isVisible = true
                            }
                            ViewState.Loading -> {
                                binding.groupEpisodes.isVisible = false
                            }
                            ViewState.Idle -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}