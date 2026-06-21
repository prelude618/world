package com.holiware.world1.viewmodel

import com.holiware.world1.model.Coin
import com.holiware.world1.model.CoinRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CoinViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: CoinRepository = mockk(relaxed = true)
    private lateinit var viewModel: CoinViewModel

    val coins = listOf(
        Coin("1", "BTC", "Bitcoin", "https://google.com", 10.01, 2.01),
        Coin("2", "ETC", "Ethereum Classic", "https://google.com", 1.01, 0.01)
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        coEvery { repository.fetchCoins() } just Runs
        coEvery { repository.getCoins() } returns flowOf(coins)

        viewModel = CoinViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test_getCoins_success`() = runTest {
        testDispatcher.scheduler.advanceUntilIdle()

        val result = viewModel.coinListState.value
        assertEquals("ETC", result[1].symbol)
    }
}
