package com.paiva.marvel.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.gson.reflect.TypeToken
import com.paiva.marvel.base.BaseUnitTest
import com.paiva.marvel.model.Heroes
import com.paiva.marvel.screens.heroesList.HeroesViewModel
import com.paiva.marvel.service.MarvelService
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HeroesViewModelTest: BaseUnitTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()

    lateinit var viewModel: HeroesViewModel

    @MockK
    lateinit var service: MarvelService


    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = spyk(HeroesViewModel(service))
    }

    @Test
    fun `fetchHeroes - with success response`() = runBlockingTest {
        coEvery { service.getHeroes() } returns getMockJson(HEROES_JSON, HEROES_TYPE)
        every { viewModel.isInternetAvailable() } answers { true }

        viewModel.fetchHeroes()

        Assert.assertEquals(20, service.getHeroes().data.count)
        Assert.assertEquals(5, viewModel.heroesCarroselItens.value?.size)
        Assert.assertEquals(15, viewModel.heroesWithoutCarroselItens.value?.size)
    }

    @Test
    fun `fetchHeroes - without internet connection`() = runBlockingTest {
        coEvery { service.getHeroes() } returns getMockJson(HEROES_JSON, HEROES_TYPE)
        every { viewModel.isInternetAvailable() } answers { false }

        viewModel.fetchHeroes()

        Assert.assertNull(viewModel.heroesCarroselItens.value?.size)
        Assert.assertNull(viewModel.heroesWithoutCarroselItens.value?.size)
        viewModel.error.value?.let { Assert.assertTrue(it) }
    }

    @Test
    fun `fetchHeroes - with api error`() = runBlockingTest {
        every { viewModel.isInternetAvailable() } answers { true }

        viewModel.fetchHeroes()

        Assert.assertNull(viewModel.heroesCarroselItens.value?.size)
        Assert.assertNull(viewModel.heroesWithoutCarroselItens.value?.size)
        viewModel.error.value?.let { Assert.assertTrue(it) }
    }

    companion object {
        private const val HEROES_JSON = "heroes.json"
        private val HEROES_TYPE =
            object : TypeToken<Heroes>() {}.type
    }
}