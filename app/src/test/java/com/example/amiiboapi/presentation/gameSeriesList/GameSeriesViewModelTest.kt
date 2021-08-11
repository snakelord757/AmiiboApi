package com.example.amiiboapi.presentation.gameSeriesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.amiiboapi.R
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.GameSeriesModel
import com.example.amiiboapi.domain.interactor.AmiiboInteractor
import com.example.amiiboapi.domain.mapper.ErrorMapper
import com.example.amiiboapi.domain.model.Error
import com.example.amiiboapi.presentation.common.SchedulerProviderTest
import io.mockk.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import java.io.InterruptedIOException

class GameSeriesViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val amiiboInteractor: AmiiboInteractor = mockk()
    private val errorMapper: ErrorMapper = mockk()
    private lateinit var gameSeriesViewModel: GameSeriesViewModel

    private val amiiboObserver: Observer<List<GameSeriesModel>> = mockk()
    private val errorObserver: Observer<Error> = mockk()
    private val showProgressBarObserver: Observer<Boolean> = mockk()

    @Before
    fun setUp() {
        gameSeriesViewModel = GameSeriesViewModel(amiiboInteractor, SchedulerProviderTest(), errorMapper)
        gameSeriesViewModel.gameSeries.observeForever(amiiboObserver)
        gameSeriesViewModel.error.observeForever(errorObserver)
        gameSeriesViewModel.showProgressBar.observeForever(showProgressBarObserver)

        every { amiiboObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
        every { showProgressBarObserver.onChanged(any()) } just Runs
    }

    @Test
    fun testAmiiboInfoReceived() {
        //Arrange
        every { amiiboInteractor.getGameSeries() } returns GAME_SERIES_RESPONSE
        val expectedResult = GAME_SERIES_RESPONSE.amiibo

        //Act
        gameSeriesViewModel.getGameSeries(false)

        //Assert
        verifySequence {
            showProgressBarObserver.onChanged(true)
            showProgressBarObserver.onChanged(false)
            amiiboObserver.onChanged(expectedResult)
        }
        verify { errorObserver wasNot Called }
    }

    @Test
    fun testConnectionLost() {
        //Arrange
        val ioException = IOException()
        every { amiiboInteractor.getGameSeries() } throws ioException
        val expectedResult = EXCEPTED_ERROR_CONNECTION_LOST
        every { errorMapper.mapError(ioException) } returns expectedResult

        //Act
        gameSeriesViewModel.getGameSeries(false)

        //Assert
        verifySequence {
            showProgressBarObserver.onChanged(true)
            showProgressBarObserver.onChanged(false)
            errorObserver.onChanged(expectedResult)
        }
        verify { amiiboObserver wasNot Called }
    }

    @Test
    fun testConnectionTimeout() {
        //Arrange
        val interruptedIOException = InterruptedIOException()
        every { amiiboInteractor.getGameSeries() } throws interruptedIOException
        val expectedResult = EXPECTED_ERROR_CONNECTION_TIMEOUT
        every { errorMapper.mapError(interruptedIOException) } returns expectedResult

        //Act
        gameSeriesViewModel.getGameSeries(false)

        //Assert
        verifySequence {
            showProgressBarObserver.onChanged(true)
            showProgressBarObserver.onChanged(false)
            errorObserver.onChanged(expectedResult)
        }
        verify { amiiboObserver wasNot Called }
    }

    @Test
    fun testBadResponseExceptionReceived() {
        //Arrange
        val badResponseException = BadResponseException(NOT_FOUND)
        every { amiiboInteractor.getGameSeries() } throws badResponseException
        val expectedResult = EXPECTED_ERROR_NOT_FOUND
        every { errorMapper.mapError(badResponseException) } returns expectedResult

        //Act
        gameSeriesViewModel.getGameSeries(false)

        //Assert
        verifySequence {
            showProgressBarObserver.onChanged(true)
            showProgressBarObserver.onChanged(false)
            errorObserver.onChanged(expectedResult)
        }
        verify { amiiboObserver wasNot Called }
    }

    companion object {
        private val GAME_SERIES_RESPONSE = Amiibo(listOf(GameSeriesModel(key = "0x000", "Super Mario")))

        private val EXCEPTED_ERROR_CONNECTION_LOST = Error(R.string.unknown_host_message)
        private val EXPECTED_ERROR_CONNECTION_TIMEOUT = Error(R.string.connection_timeout)
        private const val NOT_FOUND = 404
        private val EXPECTED_ERROR_NOT_FOUND = Error(R.string.not_found)
    }
}