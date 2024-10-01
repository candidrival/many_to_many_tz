package com.example.many_to_many_tz.presentation.all_items

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.many_to_many_tz.presentation.all_items.adapter.AllItemsAdapter
import com.example.many_to_many_tz.R
import com.example.many_to_many_tz.databinding.FragmentAllItemsBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllItemsFragment : Fragment() {

    private var _binding: FragmentAllItemsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<AllItemsViewModel>()

    private val allItemsAdapter: AllItemsAdapter by lazy {
        AllItemsAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAllItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.getItems()
        }
        initViews()
        observeState()
    }

    private fun observeState() = with(binding) {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is AllItemsViewModel.Downloaded -> {
                        progressBarAllItems.isGone = true
                        emptyAllItems.isGone = true
                        rvAllItems.isVisible = true
                        titleAllItems.apply {
                            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.item_animation_fall_down)
                            anim.reset()
                            clearAnimation()
                            startAnimation(anim)
                            text = state.items.title
                        }
                        allItemsAdapter.setItems(state.items.items)
                        rvAllItems.scheduleLayoutAnimation()
                    }

                    AllItemsViewModel.Error -> {
                        emptyAllItems.isVisible = true
                        progressBarAllItems.isGone = true
                        rvAllItems.isGone = true
                    }

                    AllItemsViewModel.Initial -> {
                        titleAllItems.text = ""
                        progressBarAllItems.isVisible = true
                        emptyAllItems.isGone = true
                        rvAllItems.isGone = true
                    }
                }
            }
        }
    }

    private fun initViews() = with(binding) {
        refreshAllItems.setOnClickListener {
            lifecycleScope.launch {
                viewModel.getItems()
            }
        }
        rvAllItems.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = allItemsAdapter
        }

        allItemsAdapter.clickListener = { item ->
            val action = AllItemsFragmentDirections.allItemsFragmentToChosenItemFragment(item.id, item.name, item.image, item.color)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}