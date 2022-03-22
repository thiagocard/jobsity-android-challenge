package com.jobsity.android.challenge.ui.favorite_shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.PagingData
import com.google.android.material.snackbar.Snackbar
import com.jobsity.android.challenge.MainActivity
import com.jobsity.android.challenge.NavigationGraphDirections
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

        val showsAdapter = ShowsPagingDataAdapter(
            onItemClick = { show ->
                (activity as MainActivity).navController
                    .navigate(NavigationGraphDirections.actionGlobalShowDetails(show.id))
            },
            onItemMenuClick = { menuView, show ->
                PopupMenu(requireContext(), menuView)
                    .apply {
                        inflate(R.menu.menu_fav_show)
                        setOnMenuItemClickListener { item ->
                            if (item.itemId == R.id.action_remove) {
                                viewModel.removeFromFavorites(show.id)
                                true
                            } else false
                        }
                    }.show()
            })

        binding.recyclerView.adapter = showsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.favoriteShows.collectLatest { favorites ->
                        if (favorites.isNotEmpty()) {
                            (activity as MainActivity).apply {
                                showFab(R.drawable.ic_baseline_sort_by_alpha_24)
                                setFabListener { viewModel.reverseOrder() }
                            }
                            binding.recyclerView.isVisible = true
                            binding.tvEmpty.isVisible = false
                            showsAdapter.submitData(PagingData.from(favorites))
                        } else {
                            (activity as MainActivity).hideFab()
                            binding.recyclerView.isVisible = false
                            binding.tvEmpty.isVisible = true
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).hideFab()
        _binding = null
    }

}