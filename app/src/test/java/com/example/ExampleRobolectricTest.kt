package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MarketRepository
import com.example.viewmodel.Screen
import com.example.viewmodel.TerminalViewModel
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("EconPulse", appName)
    }

    @Test
    fun `verify market repository assets loaded`() {
        assertTrue(MarketRepository.globalIndices.isNotEmpty())
        assertTrue(MarketRepository.stockQuotes.isNotEmpty())
        assertTrue(MarketRepository.commoditiesList.isNotEmpty())
        assertTrue(MarketRepository.aiQueryPresets.isNotEmpty())
    }

    @Test
    fun `verify terminal view model navigation and selection`() {
        val viewModel = TerminalViewModel()
        assertEquals(Screen.Terminal, viewModel.uiState.value.currentScreen)

        viewModel.navigateTo(Screen.Markets)
        assertEquals(Screen.Markets, viewModel.uiState.value.currentScreen)

        viewModel.selectStock("HDFCBANK")
        assertEquals("HDFCBANK", viewModel.uiState.value.selectedStock.symbol)

        viewModel.setTimeframe("1M")
        assertEquals("1M", viewModel.uiState.value.selectedTimeframe)

        viewModel.openOrderDialog(isBuy = true)
        assertTrue(viewModel.uiState.value.orderDialog.isOpen)
        assertTrue(viewModel.uiState.value.orderDialog.isBuy)

        viewModel.executeOrder()
        assertFalse(viewModel.uiState.value.orderDialog.isOpen)
        assertNotNull(viewModel.uiState.value.snackbarMessage)
    }
}
