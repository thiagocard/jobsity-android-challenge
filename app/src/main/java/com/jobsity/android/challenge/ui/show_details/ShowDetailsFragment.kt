package com.jobsity.android.challenge.ui.show_details

import android.os.Bundle
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
import com.jobsity.android.challenge.databinding.FragmentShowDetailsBinding
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShowDetailsFragment : Fragment() {

    private val viewModel by viewModel<ShowDetailsViewModel>()
    private var _binding: FragmentShowDetailsBinding? = null
    private val binding get() = _binding!!
//    private val args by navArgs<>()

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

//        viewModel.fetchShow()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
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
                            binding.progressBar.isVisible = false
                            binding.constraintLayout.isVisible = true
                            binding.setVariable(BR.show, state.data)
                        }
                        ViewState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.constraintLayout.isVisible = false
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