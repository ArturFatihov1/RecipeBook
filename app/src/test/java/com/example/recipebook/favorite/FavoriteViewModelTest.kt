package com.example.recipebook.favorite

import com.example.recipebook.detail.core.FakeRunAsync
import com.example.recipebook.detail.core.FakeUiObservable
import com.example.recipebook.detail.data.Recipe
import com.example.recipebook.favorite.data.FavoriteRepository
import com.example.recipebook.favorite.presentation.FavoriteUiObservable
import com.example.recipebook.favorite.presentation.FavoriteViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FavoriteViewModelTest {

    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeFavoriteUiObservable
    private lateinit var viewModel: FavoriteViewModel

    @Before
    fun setup() {
        repository = FakeRepository()
        runAsync = FakeRunAsync()
        observable = FakeFavoriteUiObservable.Base()
        viewModel = FavoriteViewModel(
            repository = repository,
            runAsync = runAsync,
            observable = observable,
        )
    }

    @Test
    fun load_favorites() {
        viewModel.load()
        var actual: FavoriteUiState = observable.postUiStateCalledList.last()
        var expected: FavoriteUiState = FavoriteUiState.Empty
        assertEquals(expected, actual)

        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.FavoriteState(favorites = listOf())
        assertEquals(expected, actual)
    }

    @Test
    fun like_recipe() {
        val recipe = Recipe(
            id = "53322",
            title = "Flan",
            imageUrl = "",
            ingredients = emptyList(),
            instructions = emptyList(),
            isLiked = true
        )

        viewModel.like(recipe)
        runAsync.returnResult()
        repository.assertSaved(recipe)

        viewModel.load()
        runAsync.returnResult()

        val actual = observable.postUiStateCalledList.last()
        val expected = FavoriteUiState.FavoriteState(favorites = listOf(recipe))
        assertEquals(expected, actual)
    }

    @Test
    fun unLike_recipe() {
        val recipe = Recipe(
            id = "53322",
            title = "Flan",
            imageUrl = "",
            ingredients = emptyList(),
            instructions = emptyList(),
            isLiked = true
        )

        viewModel.like(recipe)
        runAsync.returnResult()

        viewModel.unLike(recipeId = recipe.id)
        runAsync.returnResult()
        repository.assertRemoved(recipe.id)

        viewModel.load()
        runAsync.returnResult()

        val actual = observable.postUiStateCalledList.last()
        val expected = FavoriteUiState.FavoriteState(favorites = listOf())
        assertEquals(expected, actual)
    }
}

private class FakeRepository : FavoriteRepository {
    private val favorites = linkedMapOf<String, Recipe>()

    override suspend fun loadFavorites(): List<Recipe> =
        favorites.values.sortedWith(
            compareByDescending<Recipe> { it.isLiked }.thenBy { it.title }
        )

    override suspend fun like(recipe: Recipe) {
        val stored = recipe.copy(isLiked = true)
        favorites[recipe.id] = stored
        saved = stored
    }

    override suspend fun unLike(recipeId: String) {
        favorites.remove(recipeId)
        removedId = recipeId
    }

    private var saved: Recipe? = null
    private var removedId: String? = null

    fun assertSaved(expected: Recipe) {
        assertEquals(expected, saved)
    }

    fun assertRemoved(expectedId: String) {
        assertEquals(expectedId, removedId)
    }
}

private interface FakeFavoriteUiObservable :
    FakeUiObservable<FavoriteUiState>,
    FavoriteUiObservable {
    class Base : FakeUiObservable.Abstract<FavoriteUiState>(), FakeFavoriteUiObservable
}
