package com.example.recipebook.recipelist.presentation

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipebook.R
import com.example.recipebook.core.HandleErrorState
import com.example.recipebook.recipelist.data.RecipeWithSettings
import com.example.recipebook.recipelist.presentation.navigation.NavigateToDetail
import com.example.recipebook.recipelist.presentation.navigation.NavigateToFavorite
import com.example.recipebook.recipelist.presentation.search.SearchUiState
import com.example.recipebook.recipelist.presentation.search.UpdateSearch

interface RecipeListUiState {

    fun update(
        searchInput: UpdateSearch,
        recipeListAdapter: RecipeListAdapter,
        swipeRefresh: SwipeRefreshLayout,
        progressDialog: DialogFragment,
        errorDialog: DialogFragment,
    ) = Unit

    fun navigate(navigate: NavigateToFavorite) = Unit
    fun navigate(navigate: NavigateToDetail) = Unit
    fun showDialog(fragmentManager: FragmentManager, dialog: DialogFragment) = Unit

    abstract class AbstractDialogUiState(private val dialogTag: String) : RecipeListUiState {
        override fun showDialog(fragmentManager: FragmentManager, dialog: DialogFragment) {
            dialog.show(fragmentManager, dialogTag)
        }
    }

    object Favorites : RecipeListUiState {
        override fun navigate(navigate: NavigateToFavorite) {
            navigate.navigateToFavorite()
        }
    }

    object Detail : RecipeListUiState {
        override fun navigate(navigate: NavigateToDetail) {
            navigate.navigateToDetail()
        }
    }

    data class RecipeListState(
        private val recipes: List<RecipeWithSettings>
    ) : RecipeListUiState {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment,
        ) {
            errorDialog.dismiss()
            progressDialog.dismiss()
            recipeListAdapter.recipeList = recipes
            swipeRefresh.isRefreshing = false
        }
    }

    data class InputSufficientFocusedState(
        private val variants: List<String>
    ) : RecipeListUiState {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment
        ) {
            searchInput.update(SearchUiState.Sufficient(variants))
        }
    }

    object InputInsufficientFocusedState : RecipeListUiState {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment
        ) {
            searchInput.update(SearchUiState.Insufficient)
        }
    }

    object RefreshState : RecipeListUiState {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment,
        ) {
            errorDialog.dismiss()
            progressDialog.dismiss()
            swipeRefresh.isRefreshing = true
        }
    }

    object NextPageState : RecipeListUiState {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment,
        ) {
            errorDialog.dismiss()
            progressDialog.dismiss()
            swipeRefresh.isRefreshing = true
        }
    }

    object LoadingState : AbstractDialogUiState("LoadingDialog") {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment,
        ) {
            errorDialog.dismiss()
            swipeRefresh.isRefreshing = false
        }
    }


    data class ErrorState(
        private val error: HandleErrorState = HandleErrorState(stringRes = R.string.internet_connection_failed)
    ) : AbstractDialogUiState("ErrorDialog") {

        override fun update(
            searchInput: UpdateSearch,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: DialogFragment,
            errorDialog: DialogFragment,
        ) {
            progressDialog.dismiss()
            swipeRefresh.isRefreshing = false
        }
    }
}