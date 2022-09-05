package com.jobsity.android.challenge.ui.episode_details

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
import com.jobsity.android.challenge.databinding.FragmentEpisodeDetailsBinding
import com.jobsity.android.challenge.databinding.FragmentShowDetailsBinding
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class EpisodeDetailsFragment : Fragment() {

    private val viewModel by viewModel<EpisodeDetailsViewModel>()
    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!
    private val args by navArgs<EpisodeDetailsFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEpisodeId(args.episodeId)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.episode.collectLatest { state ->
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
                                (activity as MainActivity).binding.toolbar.subtitle = state.data.seasonAndEpisode()
                                binding.progressBar.isVisible = false
                                binding.constraintLayout.isVisible = true
                                binding.setVariable(BR.episode, state.data)
                            }
                            ViewState.Loading -> {
                                binding.progressBar.isVisible = true
                                binding.constraintLayout.isVisible = false
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