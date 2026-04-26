package com.example.recipebook.detail.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipebook.databinding.FragmentDetailBinding
import com.example.recipebook.detail.DetailUiState

class DetailFragment : AbstractFragment<GameUiState, GameViewModel>() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override val update: (DetailUiState) -> Unit = { uiState ->
        uiState.update(
            binding.titleHeader,
            binding.detailImage,
            binding.recipeLike,
            binding.ingredientsList,
            binding.instructionsList
        )
        uiState.navigate(requireActivity() as NavigateToBack)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            (requireActivity() as ProvideViewModel).makeViewModel(DetailViewModel::class.java)

        val recipeId = arguments?.getInt(DetailScreen.KEY_ID) ?: -1

        binding.recipeLike.setOnClickListener {
            viewModel.toggleLike(id = recipeId)
        }

        binding.backButton.setOnClickListener {
            viewModel.back()
        }

        if (savedInstanceState == null) {
            viewModel.init(id = recipeId)
            viewModel.progress(id = recipeId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}