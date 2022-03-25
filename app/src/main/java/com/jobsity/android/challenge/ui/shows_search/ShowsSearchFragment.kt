package com.jobsity.android.challenge.ui.shows_search

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
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
import com.jobsity.android.challenge.databinding.FragmentShowsSearchBinding
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.shows.ShowsPagingDataAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowsSearchFragment : Fragment() {

    private val viewModel by viewModel<ShowsSearchViewModel>()

    private var _binding: FragmentShowsSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_search, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        (menu.findItem(R.id.search_view)?.actionView as? SearchView)?.apply {
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.setQuery(query)
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showsAdapter = ShowsPagingDataAdapter(
            onItemClick = { show ->
                (activity as MainActivity).navController
                    .navigate(R.id.show_details, bundleOf("show_id" to show.id))
            }
        )

        binding.recyclerView.adapter = showsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.result.collectLatest { state ->
                        when (state) {
                            is ViewState.Error -> {
                                stopLoading()
                                Snackbar.make(
                                    binding.root,
                                    state.errorMessage() ?: "",
                                    Snackbar.LENGTH_LONG
                                ).show()
                            }
                            is ViewState.Loaded -> {
                                stopLoading()
                                if (state.data.shows.isNotEmpty()) {
                                    val adapter = ShowsPagingDataAdapter(
                                        onItemClick = { show ->
                                            (activity as MainActivity).navController
                                                .navigate(
                                                    NavigationGraphDirections.actionGlobalShowDetails(
                                                        show.id)
                                                )
                                        }
                                    )
                                    binding.recyclerView.adapter = adapter
                                    adapter.submitData(PagingData.from(state.data.shows))
                                } else {
                                    Snackbar.make(
                                        binding.root,
                                        getString(R.string.no_shows_found),
                                        Snackbar.LENGTH_LONG
                                    ).show()
                                }
                            }
                            ViewState.Loading -> {
                                binding.progressBar.isVisible = true
                                binding.recyclerView.isVisible = false
                            }
                            ViewState.Idle -> {
                                stopLoading()
                            }
                        }
                    }
                }
            }
        }
    }

    private fun stopLoading() {
        binding.progressBar.isVisible = false
        binding.recyclerView.isVisible = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.recyclerView?.adapter = null
        _binding = null
    }

}