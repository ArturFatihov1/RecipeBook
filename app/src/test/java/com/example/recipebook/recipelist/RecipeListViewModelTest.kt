package com.example.recipebook.recipelist

import com.example.recipebook.core.FakeClearViewModel
import com.example.recipebook.core.FakeFragment
import com.example.recipebook.core.FakeRunAsync
import com.example.recipebook.recipelist.FakeRecipeListRepository.Companion.AMOUNT_RECIPES
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import kotlin.collections.first

class RecipeListViewModelTest {

    private lateinit var repository: FakeRecipeListRepository
    private lateinit var observable: FakeRecipeListUiObservable.Base
    private lateinit var viewModel: RecipeListViewModel
    private lateinit var runAsync: FakeRunAsync
    private lateinit var clearViewModel: FakeClearViewModel
    private lateinit var fragment: FakeFragment<RecipeListUiState>

    @Before
    fun setup() {
        repository = FakeRecipeListRepository()
        observable = FakeRecipeListUiObservable.Base()
        runAsync = FakeRunAsync()
        clearViewModel = FakeClearViewModel()
        viewModel = RecipeListViewModel(
            observable = observable,
            clearViewModel = clearViewModel,
            repository = repository,
            runAsync = runAsync,
            handleError = HandleError.DomainToUi()
        )
        fragment = FakeFragment<RecipeListUiState>()
    }

    @Test
    fun sameFragment() {
        repository.expectFailure()

        viewModel.load()
        assertEquals(RecipeListUiState.LoadingState, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)
        assertEquals(RecipeListUiState.LoadingState, fragment.stateList.first())
        assertEquals(1, fragment.stateList.size)

        runAsync.returnResult()
        assertEquals(RecipeListUiState.ErrorState(), observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)
        assertEquals(RecipeListUiState.ErrorState(), fragment.stateList[1])
        assertEquals(2, fragment.stateList.size)
        clearViewModel.clear(RecipeListViewModel::class.java)
        clearViewModel.assertClearCalled(RecipeListViewModel::class.java)
    }

    @Test
    fun recreateActivity() {
        repository.expectFailure()

        viewModel.load()
        assertEquals(RecipeListUiState.LoadingState, observable.postUiStateCalledList.first())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        viewModel.startUpdates(observer = fragment)
        assertEquals(1, observable.registerCalledCount)
        assertEquals(RecipeListUiState.LoadingState, fragment.stateList.first())
        assertEquals(1, fragment.stateList.size)

        viewModel.stopUpdates()
        assertEquals(1, observable.unregisterCalledCount)
        assertEquals(1, fragment.stateList.size)

        runAsync.returnResult()
        assertEquals(1, fragment.stateList.size)
        assertEquals(RecipeListUiState.ErrorState(), observable.postUiStateCalledList[1])
        assertEquals(2, observable.postUiStateCalledList.size)

        fragment = FakeFragment<RecipeListUiState>()

        viewModel.load()

        assertEquals(1, repository.loadCalledCount)
        assertEquals(2, observable.postUiStateCalledList.size)

        viewModel.startUpdates(observer = fragment)
        assertEquals(2, observable.registerCalledCount)
        assertEquals(RecipeListUiState.ErrorState(), fragment.stateList.first())
        assertEquals(1, fragment.stateList.size)

        viewModel.load()
        assertEquals(RecipeListUiState.LoadingState, observable.postUiStateCalledList.last())
        assertEquals(3, observable.postUiStateCalledList.size)
        assertEquals(2, repository.loadCalledCount)

        runAsync.returnResult()
        assertEquals(3, fragment.stateList.size)
        assertEquals(RecipeListUiState.ErrorState(), observable.postUiStateCalledList.last())
        assertEquals(4, observable.postUiStateCalledList.size)
    }

    @Test
    fun searchTest() {
        viewModel.load()
        runAsync.returnResult()
        var actual = observable.postUiStateCalledList.last()
        var expected: RecipeListUiState = RecipeListUiState.RecipeListState(recipes = repository.recipes)
        assertEquals(expected, actual)

        viewModel.handleUserInput(text = "Ca")
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.InputInsufficientFocusedState
        assertEquals(expected, actual)

        viewModel.handleUserInput(text = "Car")
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.InputSufficientFocusedState(
            variants = listOf(
                "Carrot Pie",
                "Carrot with rice",
                "Carrot Juice"
            )
        )
        assertEquals(expected, actual)

        viewModel.chooseSearchVariant(variant = "Carrot Pie")
        assertEquals(RecipeListUiState.RefreshState, observable.postUiStateCalledList.last())
        assertEquals(1, observable.postUiStateCalledList.size)
        assertEquals(1, repository.loadCalledCount)

        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(
            recipes = repository.recipes.filter { it.title.contains("Carrot Pie") }.take(5)
        )
        assertEquals(expected, actual)

        viewModel.load()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(
            recipes = repository.recipes.filter { it.title.contains("") }.take(5)
        )
        assertEquals(expected, actual)
    }

    @Test
    fun addFavoritesTest() {
        val firstPage = repository.recipes.take(AMOUNT_RECIPES)
        viewModel.load()
        runAsync.returnResult()
        var actual = observable.postUiStateCalledList.last()
        var expected: RecipeListUiState = RecipeListUiState.RecipeListState(recipes = repository.recipes)
        assertEquals(expected, actual)
        assertEquals(1, repository.loadCalledCount)

        val recipeId = firstPage.first().id
        viewModel.toggleFavoriteRecipe(id = recipeId)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(recipes = firstPage.clickFavoriteRecipe(recipeId, true))
        assertEquals(expected, actual)
        assertEquals(2, repository.loadCalledCount)

        viewModel.toggleFavoriteRecipe(id = repository.recipes.first().id)
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(recipes = firstPage.clickFavoriteRecipe(recipeId, false))
        assertEquals(expected, actual)
        assertEquals(3, repository.loadCalledCount)
    }

    @Test
    fun updateRecipesTest() {
        val firstPage = repository.recipes.take(AMOUNT_RECIPES)
        viewModel.load()
        runAsync.returnResult()
        var actual = observable.postUiStateCalledList.last()
        var expected: RecipeListUiState =
            RecipeListUiState.RecipeListState(recipes = firstPage)
        assertEquals(expected, actual)
        assertEquals(1, repository.loadCalledCount)

        viewModel.updateRecipes()
        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(recipes = firstPage)
        assertEquals(expected, actual)
        assertEquals(2, repository.loadCalledCount)
    }

    @Test
    fun pagingTest() {
        viewModel.load()
        runAsync.returnResult()
        var actual = observable.postUiStateCalledList.last()
        var expected: RecipeListUiState =
            RecipeListUiState.RecipeListState(recipes = repository.recipes.take(AMOUNT_RECIPES))
        assertEquals(expected, actual)
        assertEquals(1, repository.loadCalledCount)

        viewModel.loadMoreRecipes(page = repository.currentPage)
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.NextPageState
        assertEquals(expected, actual)

        runAsync.returnResult()
        actual = observable.postUiStateCalledList.last()
        expected = RecipeListUiState.RecipeListState(
            recipes = repository.recipes.take(AMOUNT_RECIPES + AMOUNT_RECIPES)
        )
        assertEquals(expected, actual)
        assertEquals(2, repository.loadCalledCount)
    }

    @Test
    fun navigateToDetail() {
        val firstPage = repository.recipes.take(AMOUNT_RECIPES)

        viewModel.load()
        runAsync.returnResult()
        var actual = observable.postUiStateCalledList.last()
        var expected: RecipeListUiState =
            RecipeListUiState.RecipeListState(recipes = firstPage)
        assertEquals(expected, actual)
        assertEquals(1, repository.loadCalledCount)

        viewModel.detailRecipe(recipeId = firstPage.first().id)
        runAsync.returnResult()
        actual = uiObservable.postUiStateCalledList.last()
        expected = RecipeListUiState.Detail
        assertEquals(expected, actual)
        clearViewModel.assertClearCalled(RecipeListViewModel::class.java)
    }

    @Test
    fun navigateToFavorites() {
        viewModel.favoriteRecipes()
        runAsync.returnResult()
        val actual = uiObservable.postUiStateCalledList.last()
        val expected = RecipeListUiState.Favorites
        assertEquals(expected, actual)
        clearViewModel.assertClearCalled(RecipeListViewModel::class.java)
    }

    private fun List<RecipeWithSettings>.clickFavoriteRecipe(
        id: String,
        isFavorite: Boolean
    ): List<RecipeWithSettings> = map { if (it.id == id) it.copy(isFavorite = isFavorite) else it }
}

class FakeRecipeListRepository : RecipeListRepository {
    var recipes: List<RecipeWithSettings> = listOf(
        RecipeWithSettings(
            "53322",
            "Flan",
            "https://www.themealdb.com/images/media/meals/0s80wo1764374393.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53256",
            "Cacik",
            "https://www.themealdb.com/images/media/meals/16zbeu1763789342.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53220",
            "kabse",
            "https://www.themealdb.com/images/media/meals/utqnjv1763598650.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53133",
            "Asado",
            "https://www.themealdb.com/images/media/meals/kgfh3q1763075438.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53086",
            "Migas",
            "https://www.themealdb.com/images/media/meals/xd9aj21740432378.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52977",
            "Corba",
            "https://www.themealdb.com/images/media/meals/58oia61564916529.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53139",
            "Fainá",
            "https://www.themealdb.com/images/media/meals/849jd81763075251.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53216",
            "Knafeh",
            "https://www.themealdb.com/images/media/meals/76nffj1763593933.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53151",
            "Paella",
            "https://www.themealdb.com/images/media/meals/9bl20p1763248192.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53026",
            "Tamiya",
            "https://www.themealdb.com/images/media/meals/n3xxd91598732796.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53266",
            "Falafel",
            "https://www.themealdb.com/images/media/meals/u5e9qq1763795441.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53013",
            "Big Mac",
            "https://www.themealdb.com/images/media/meals/urzj1d1587670726.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53310",
            "Challah",
            "https://www.themealdb.com/images/media/meals/fm01ky1764366365.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53027",
            "Koshari",
            "https://www.themealdb.com/images/media/meals/4er7mj1598733193.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52971",
            "Kafteji",
            "https://www.themealdb.com/images/media/meals/1bsv1q1560459826.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52785",
            "Dal fry",
            "https://www.themealdb.com/images/media/meals/wuxrtu1483564410.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52844",
            "Lasagne",
            "https://www.themealdb.com/images/media/meals/wtsvxx1511296896.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52769",
            "Kapsalon",
            "https://www.themealdb.com/images/media/meals/sxysrt1468240488.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53223",
            "Mutabbaq",
            "https://www.themealdb.com/images/media/meals/i5o2b61763739053.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "53028",
            "Shawarma",
            "https://www.themealdb.com/images/media/meals/kcv6hj1598733479.jpg",
            false,
            emptyList(),
            emptyList()
        ),
        RecipeWithSettings(
            "52897",
            "Carrot Cake",
            "https://www.themealdb.com/images/media/meals/vrspxv1511722107.jpg",
            false,
            emptyList(),
            emptyList()
        )
    )
    var loadCalledCount = 0
    var currentPage = 0
        private set
    private var exception: Exception? = null
    private var cachedSearchQuery: String = ""

    override suspend fun load(
        page: Int = 0,
        searchQuery: String = "",
        amountRecipes: Int = AMOUNT_RECIPES
    ): List<RecipeWithSettings> {
        loadCalledCount++
        exception?.let { throw it }
        cachedSearchQuery = searchQuery
        val recipes = recipes
            .filter { it.title.contains(cachedSearchQuery) }
            .drop(page)
            .take(page + amountRecipes)

        if (page == 0) currentPage = recipes.size else currentPage += recipes.size
        return recipes
    }

    override suspend fun searchRecipes(searchQuery: String): List<String> {
        if (searchQuery.length < MIN_SEARCH_LENGTH) return emptyList()
        return recipes.filter { it.title.contains(searchQuery) }.take(AMOUNT_RECIPES).map { it.title }
    }

    override fun toggleFavoriteRecipe(id: String) {
        val updatedRecipes = recipes.map { if (it.id == id) it.copy(isFavorite = !it.isFavorite) else it }
        recipes = updatedRecipes
    }

    fun expectFailure() {
        this.exception = NoInternetConnectionException()
    }

    companion object {
        const val AMOUNT_RECIPES = 10
        private const val MIN_SEARCH_LENGTH = 3
    }
}

data class RecipeWithSettings(
    val id: String,
    val title: String,
    val imageUrl: String,
    val isFavorite: Boolean,
    val ingredients: List<Ingredient>,
    val instructions: List<String>,
)

data class Ingredient(
    val id: String,
    val name: String,
    val measure: String,
)