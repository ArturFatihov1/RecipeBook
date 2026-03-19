package com.example.recipebook.detail.detail

import com.example.recipebook.detail.core.FakeRunAsync
import com.example.recipebook.detail.core.FakeUiObservable
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
            observable = observable,
            clearViewModel = FakeClearViewModel()
        )
    }

    @Test
    fun check_detail_recipe() {
        viewModel.init()
        runAsync.returnResult()
        var actual: DetailUiState = observable.postUiStateCalledList.last()
        var expected: DetailUiState = DetailUiState.Initial(
            title = "Flan",
            ingredients = listOf<Ingredient>(
                Ingredient("1", "Sugar", "100g"),
                Ingredient("2", "Milk", "350g"),
                Ingredient("3", "Sugar", "45g")
            ),
            instruction = listOf<String>(
                "For the caramel place 100 grams of sugar",
                "in a frying pan without any fat and melt on medium heat."
            )
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

        viewModel.success()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.IngredientSuccessState
        assertEquals(expected, actual)

        viewModel.like()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.RecipeLikeState
        assertEquals(expected, actual)

        viewModel.unLike()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.RecipeUnLikeState
        assertEquals(expected, actual)

        viewModel.back()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = DetailUiState.Leave
        assertEquals(expected, actual)
        assertEquals(true, repository.clearCalled)
    }
}

private class FakeRepository : DetailRepository {
    private val list: List<Recipe> = listOf(
        Recipe(
            title = "Flan",
            ingredients = listOf<Ingredient>(
                Ingredient("1", "Sugar", "100g"),
                Ingredient("2", "Milk", "350g"),
                Ingredient("3", "Sugar", "45g")
            ),
            instruction = listOf<String>(
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
}

private interface FakeDetailUiObservable : FakeUiObservable<DetailUiState>, DetailUiObservable {
    class Base : FakeUiObservable.Abstract<DetailUiState>(), FakeDetailUiObservable
}
