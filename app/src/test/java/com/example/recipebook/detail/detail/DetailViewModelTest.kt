package com.example.recipebook.detail.detail

import com.example.recipebook.detail.DetailUiState
import com.example.recipebook.detail.IngredientUiState
import com.example.recipebook.detail.core.FakeRunAsync
import com.example.recipebook.detail.core.FakeUiObservable
import com.example.recipebook.detail.data.DetailRepository
import com.example.recipebook.detail.data.Ingredient
import com.example.recipebook.detail.data.Recipe
import com.example.recipebook.detail.presentation.DetailUiObservable
import com.example.recipebook.detail.presentation.DetailViewModel
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test


class DetailViewModelTest {
    private lateinit var viewModel: DetailViewModel
    private lateinit var repository: FakeRepository
    private lateinit var runAsync: FakeRunAsync
    private lateinit var observable: FakeDetailUiObservable

    @Before
    fun setup() {
        repository = FakeRepository()
        runAsync = FakeRunAsync()
        observable = FakeDetailUiObservable.Base()
        viewModel = DetailViewModel(
            repository = repository,
            runAsync = runAsync,
            clearViewModel = FakeClearViewModel(),
            observable
        )
    }

    @Test
    fun check_detail_recipe() {
        viewModel.init(0)
        runAsync.returnResult()
        var actual: DetailUiState = observable.postUiStateCalledList.last()
        var expected: DetailUiState = DetailUiState.Initial(
            title = "Flan",
            imageUrl = "url",
            ingredients = listOf(
                IngredientUiState.Initial("Sugar", "100g"),
                IngredientUiState.Initial("Milk", "350g"),
                IngredientUiState.Initial("Sugar", "45g")
            ),
            instructions = listOf<String>(
                "For the caramel place 100 grams of sugar",
                "in a frying pan without any fat and melt on medium heat."
            ),
            isLiked = false
        )
        assertEquals(expected, actual)

        viewModel.progress()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.IngredientProgressState
        assertEquals(expected, actual)

        viewModel.error()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.IngredientErrorState
        assertEquals(expected, actual)

        viewModel.success(0)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.IngredientSuccessState(
            ingredients = listOf(
                IngredientUiState.Success("image"),
                IngredientUiState.Success("image")
            )
        )
        assertEquals(expected, actual)

        viewModel.like(0)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.RecipeLikeState
        assertEquals(expected, actual)

        viewModel.unLike(0)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.RecipeUnLikeState
        assertEquals(expected, actual)

        viewModel.back()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.Leave
        assertEquals(expected, actual)
    }
}

private class FakeRepository : DetailRepository {
    private val list: List<Recipe> = listOf(
        Recipe(
            id = "0",
            title = "Flan",
            imageUrl = "url",
            ingredients = listOf<Ingredient>(
                Ingredient("1", "url", "Sugar", "100g"),
                Ingredient("2", "url", "Milk", "350g"),
                Ingredient("3", "url", "Sugar", "45g")
            ),
            instructions = listOf<String>(
                "For the caramel place 100 grams of sugar",
                "in a frying pan without any fat and melt on medium heat."
            ),
            isLiked = false
        )
    )

    override suspend fun recipe(id: Int): Recipe {
        return list[id]
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return list[id].isLiked
    }

    override fun likeRecipe(id: Int) {
        list[id].isLiked = true
    }

    override fun unLikeRecipe(id: Int) {
        list[id].isLiked = false
    }

    override fun clear() = Unit
}

private interface FakeDetailUiObservable : FakeUiObservable<DetailUiState>, DetailUiObservable {
    class Base : FakeUiObservable.Abstract<DetailUiState>(), FakeDetailUiObservable
}
