package com.jobsity.android.challenge.ui.shows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.jobsity.android.challenge.MainActivity
import com.jobsity.android.challenge.NavigationGraphDirections
import com.jobsity.android.challenge.databinding.FragmentShowsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowsFragment : Fragment() {

    private val viewModel by viewModel<ShowsViewModel>()

    private var _binding: FragmentShowsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val showsAdapter = ShowsPagingDataAdapter(
            onItemClick = { show ->
                (activity as MainActivity).navController
                    .navigate(NavigationGraphDirections.actionGlobalShowDetails(show.id))
            }
        )

        binding.recyclerView.adapter = showsAdapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.shows.collectLatest { pagingData ->
                        showsAdapter.submitData(pagingData)
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