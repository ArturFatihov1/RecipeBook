package com.example.recipebook.favorite

import com.example.recipebook.FakeRunAsync
import com.example.recipebook.FakeUiObservable
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
        viewModel.init()
        var actual: FavoriteUiState = observable.postUiStateCalledList.last()
        var expected: FavoriteUiState = FavoriteUiState.Empty
        assertEquals(expected, actual)

        viewModel.load()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.Empty
        assertEquals(expected, actual)

        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.Content(favorites = listOf<FavoriteRecipe>())
        assertEquals(expected, actual)
    }

    @Test
    fun like_recipe() {
        val recipe = FavoriteRecipe(id = "53322", title = "Flan")

        viewModel.init()
        var actual: FavoriteUiState = observable.postUiStateCalledList.last()
        var expected: FavoriteUiState = FavoriteUiState.Empty
        assertEquals(expected, actual)

        viewModel.like(recipe)
        runAsync.returnResult()
        repository.assertSaved(recipe)

        viewModel.load()
        runAsync.returnResult()

        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.Content(favorites = listOf(recipe))
        assertEquals(expected, actual)
    }

    @Test
    fun unLike_recipe() {
        val recipe = FavoriteRecipe(id = "53322", title = "Flan")

        viewModel.init()
        var actual: FavoriteUiState = observable.postUiStateCalledList.last()
        var expected: FavoriteUiState = FavoriteUiState.Empty
        assertEquals(expected, actual)

        viewModel.like(recipe)
        runAsync.returnResult()

        viewModel.unLike(recipeId = recipe.id)
        runAsync.returnResult()
        repository.assertRemoved(recipe.id)

        viewModel.load()
        runAsync.returnResult()

        actual = observable.postUiStateCalledList.last()
        expected = FavoriteUiState.Content(favorites = listOf<FavoriteRecipe>())
        assertEquals(expected, actual)
    }
}

private class FakeRepository : FavoriteRepository {
    private val favorites = linkedMapOf<String, FavoriteRecipe>()

    override suspend fun loadFavorites(): List<FavoriteRecipe> = favorites.values.toList()

    override suspend fun like(recipe: FavoriteRecipe) {
        favorites[recipe.id] = recipe
        saved = recipe
    }

    override suspend fun unLike(recipeId: String) {
        favorites.remove(recipeId)
        removedId = recipeId
    }

    private var saved: FavoriteRecipe? = null
    private var removedId: String? = null

    fun assertSaved(expected: FavoriteRecipe) {
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

