package com.jobsity.android.challenge.ui.favorite_shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.jobsity.android.challenge.MainActivity
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.databinding.FragmentFavoriteShowsBinding
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

        val showsAdapter = ShowsPagingDataAdapter { show ->
            (activity as MainActivity).navController
                .navigate(FavoriteShowsFragmentDirections.actionShowsToShowDetails(show.id))
        }

        binding.recyclerView.adapter = showsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.fetchFavorites().collectLatest { favorites ->
                        if (favorites.isNotEmpty()) {
                            showsAdapter.submitData(PagingData.from(favorites))
                        } else {
                            Snackbar.make(
                                (activity as MainActivity).binding.coordinatorLayout,
                                R.string.no_shows_found,
                                Snackbar.LENGTH_LONG
                            ).show()
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