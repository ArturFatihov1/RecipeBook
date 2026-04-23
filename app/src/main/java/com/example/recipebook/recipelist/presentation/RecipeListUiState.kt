package com.example.recipebook.recipelist.presentation

import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.recipebook.core.HandleErrorState
import com.example.recipebook.recipelist.data.RecipeWithSettings

interface RecipeListUiState {

    fun update(
        searchInput: AutoCompleteTextView,
        recipeListAdapter: RecipeListAdapter,
        swipeRefresh: SwipeRefreshLayout,
        progressDialog: AlertDialog,
        errorDialog: AlertDialog,
    ) = Unit

    fun navigate(navigate: NavigateToFavorite) = Unit
    fun navigate(navigate: NavigateToDetail) = Unit

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
            searchInput: AutoCompleteTextView,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: AlertDialog,
            errorDialog: AlertDialog
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

    }

    object RefreshState : RecipeListUiState {
        override fun update(
            searchInput: AutoCompleteTextView,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: AlertDialog,
            errorDialog: AlertDialog
        ) {
            errorDialog.dismiss()
            progressDialog.dismiss()
            swipeRefresh.isRefreshing = true
        }
    }

    object NextPageState : RecipeListUiState {
        override fun update(
            searchInput: AutoCompleteTextView,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: AlertDialog,
            errorDialog: AlertDialog
        ) {
            errorDialog.dismiss()
            progressDialog.dismiss()
            swipeRefresh.isRefreshing = true
        }
    }

    object LoadingState : RecipeListUiState {
        override fun update(
            searchInput: AutoCompleteTextView,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: AlertDialog,
            errorDialog: AlertDialog
        ) {
            errorDialog.dismiss()
            swipeRefresh.isRefreshing = false
            progressDialog.show()
        }
    }

    data class ErrorState(private val error: HandleErrorState) : RecipeListUiState {
        override fun update(
            searchInput: AutoCompleteTextView,
            recipeListAdapter: RecipeListAdapter,
            swipeRefresh: SwipeRefreshLayout,
            progressDialog: AlertDialog,
            errorDialog: AlertDialog
        ) {
            errorDialog.setMessage(error.message.ifBlank { progressDialog.context.getString(error.stringRes) })
            errorDialog.show()
            swipeRefresh.isRefreshing = false
            progressDialog.dismiss()
        }
    }
}