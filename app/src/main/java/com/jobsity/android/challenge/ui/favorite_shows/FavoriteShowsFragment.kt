package com.jobsity.android.challenge.ui.favorite_shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.jobsity.android.challenge.MainActivity
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.databinding.FragmentFavoriteShowsBinding
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.shows.ShowsPagingDataAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteShowsFragment : Fragment() {

    private val viewModel by viewModel<FavoriteShowsViewModel>()

    private var _binding: FragmentFavoriteShowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.favorites.collectLatest { state ->
                    when (state) {
                        is ViewState.Error -> {
                            binding.recyclerView.isVisible = true
                            binding.progressBar.isVisible = false
                            Snackbar.make(
                                binding.root,
                                R.string.no_favorites_found,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        ViewState.Idle -> {}
                        is ViewState.Loaded -> {
                            displayFavorites(state)
                        }
                        ViewState.Loading -> {
                            binding.recyclerView.isVisible = false
                            binding.progressBar.isVisible = true
                        }
                    }

                }
            }
        }
    }

    private suspend fun displayFavorites(state: ViewState<ShowsAtList>) {
        binding.recyclerView.isVisible = true
        binding.progressBar.isVisible = false
        val shows = state.dataOrThrow().shows
        if (shows.isNotEmpty()) {
            val showsAdapter = ShowsPagingDataAdapter { show ->
                (activity as MainActivity).navController
                    .navigate(FavoriteShowsFragmentDirections.actionShowsToShowDetails(show.id))
            }
            binding.recyclerView.adapter = showsAdapter
            showsAdapter.submitData(PagingData.from(shows))
        } else {
            Snackbar.make(
                (activity as MainActivity).binding.coordinatorLayout,
                R.string.no_shows_found,
                Snackbar.LENGTH_LONG
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}