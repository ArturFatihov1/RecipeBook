package com.example.recipebook.recipelist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recipebook.core.AbstractFragment
import com.example.recipebook.databinding.FragmentRecipeListBinding
import com.example.recipebook.recipelist.presentation.navigation.NavigateToDetail
import com.example.recipebook.recipelist.presentation.navigation.NavigateToFavorite

class RecipeListFragment : AbstractFragment<RecipeListUiState, FragmentRecipeListBinding, RecipeListViewModel>() {

    private lateinit var recipesAdapter: RecipeListAdapter
    private lateinit var loadingDialog: LoadingDialogFragment
    private lateinit var errorDialog: ErrorDialogFragment

    override fun update(): (RecipeListUiState) -> Unit = { uiState ->

        uiState.update(
            searchInput = binding.searchView,
            recipeListAdapter = recipesAdapter,
            swipeRefresh = binding.swipeRefresh,
            progressDialog = loadingDialog,
            errorDialog = errorDialog
        )
        uiState.showDialog(parentFragmentManager, loadingDialog)
        uiState.showDialog(parentFragmentManager, errorDialog)
        uiState.navigate(requireActivity() as NavigateToFavorite)
        uiState.navigate(requireActivity() as NavigateToDetail)
    }

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit

        override fun afterTextChanged(editable: Editable?) {
            viewModel.handleUserInput(input = editable.toString())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        recipesAdapter = RecipeListAdapter(viewModel)
        loadingDialog = LoadingDialogFragment()
        errorDialog = ErrorDialogFragment()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun inflate(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRecipeListBinding =
        FragmentRecipeListBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            favoriteButton.setOnClickListener { viewModel.favoriteRecipes() }
            searchView.onSufficient(viewModel::searchVariants)
            searchView.onItemClicked(viewModel::load)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.searchView.addTextChangedListener(textWatcher)
    }

    override fun onPause() {
        super.onPause()
        binding.searchView.removeTextChangedListener(textWatcher)
    }
}