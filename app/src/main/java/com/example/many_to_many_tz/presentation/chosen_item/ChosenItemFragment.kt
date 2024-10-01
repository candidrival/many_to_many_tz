package com.example.many_to_many_tz.presentation.chosen_item

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.many_to_many_tz.utils.baseUrl
import com.example.many_to_many_tz.R
import com.example.many_to_many_tz.databinding.FragmentChosenItemBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChosenItemFragment : Fragment() {

    private var _binding: FragmentChosenItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ChosenItemViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChosenItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val item = ChosenItemFragmentArgs.fromBundle(requireArguments())
        lifecycleScope.launch {
            viewModel.getItems(item.itemId)
        }
        initView(item)
        observeState()
    }

    private fun initView(item: ChosenItemFragmentArgs) = with(binding) {
        titleChosenItems.text = item.name
        val imageUrl =
            if (item.image.isNotEmpty()) baseUrl + item.image else baseUrl + "/images/${item.itemId}.png"
        Glide.with(requireContext()).load(imageUrl).into(ivChosenItems)
        layoutChosenItems.apply {
            var drawableBg = background
            drawableBg = DrawableCompat.wrap(drawableBg)
            DrawableCompat.setTint(drawableBg, Color.parseColor("#${item.color}"))
            background = drawableBg
        }
        backChosenItems.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observeState() = with(binding) {
        lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {
                    is ChosenItemViewModel.Downloaded -> {
                        progressBarChosenItems.isGone = true
                        emptyChosenItems.isGone = true
                        layoutChosenItems.isVisible = true
                        tvChosenItems.text = state.item.text
                        layoutChosenItems.apply {
                            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.item_animation_fall_down)
                            anim.reset()
                            clearAnimation()
                            startAnimation(anim)
                        }
                    }

                    ChosenItemViewModel.Error -> {
                        emptyChosenItems.isVisible = true
                        progressBarChosenItems.isGone = true
                        layoutChosenItems.isGone = true
                    }

                    ChosenItemViewModel.Initial -> {
                        progressBarChosenItems.isVisible = true
                        emptyChosenItems.isGone = true
                        layoutChosenItems.isGone = true
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