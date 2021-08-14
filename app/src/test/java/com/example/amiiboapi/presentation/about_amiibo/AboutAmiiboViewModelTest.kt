package com.example.amiiboapi.presentation.about_amiibo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.amiiboapi.R
import com.example.amiiboapi.data.exception.BadResponseException
import com.example.amiiboapi.data.model.Amiibo
import com.example.amiiboapi.data.model.AmiiboModel
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

class AboutAmiiboViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    private val amiiboInteractor: AmiiboInteractor = mockk()
    private val errorMapper: ErrorMapper = mockk()
    private lateinit var aboutAmiiboViewModel: AboutAmiiboViewModel

    private val amiiboObserver: Observer<AmiiboModel> = mockk()
    private val errorObserver: Observer<Error> = mockk()
    private val showProgressBarObserver: Observer<Boolean> = mockk()

    @Before
    fun setUp() {
        aboutAmiiboViewModel = AboutAmiiboViewModel(amiiboInteractor, SchedulerProviderTest(), errorMapper)
        aboutAmiiboViewModel.amiibo.observeForever(amiiboObserver)
        aboutAmiiboViewModel.error.observeForever(errorObserver)
        aboutAmiiboViewModel.showProgressBar.observeForever(showProgressBarObserver)

        every { amiiboObserver.onChanged(any()) } just Runs
        every { errorObserver.onChanged(any()) } just Runs
        every { showProgressBarObserver.onChanged(any()) } just Runs
    }

    @Test
    fun testAmiiboInfoReceived() {
        //Arrange
        every { amiiboInteractor.getInfoAboutAmiibo(AMIIBO_TAIL, true) } returns AMIIBO_INFO_EXPECTED_RESPONSE
        val expectedResult = AMIIBO_INFO_EXPECTED_RESPONSE.amiibo.first()

        //Act
        aboutAmiiboViewModel.getInfoAbout(AMIIBO_TAIL, true)

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
        every { amiiboInteractor.getInfoAboutAmiibo(AMIIBO_TAIL, true) } throws ioException
        val expectedResult = EXCEPTED_ERROR_CONNECTION_LOST
        every { errorMapper.mapError(ioException) } returns expectedResult

        //Act
        aboutAmiiboViewModel.getInfoAbout(AMIIBO_TAIL, true)

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
        every { amiiboInteractor.getInfoAboutAmiibo(AMIIBO_TAIL, true) } throws interruptedIOException
        val expectedResult = EXPECTED_ERROR_CONNECTION_TIMEOUT
        every { errorMapper.mapError(interruptedIOException) } returns expectedResult

        //Act
        aboutAmiiboViewModel.getInfoAbout(AMIIBO_TAIL, true)

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
        every { amiiboInteractor.getInfoAboutAmiibo(AMIIBO_TAIL, true) } throws badResponseException
        val expectedResult = EXPECTED_ERROR_NOT_FOUND
        every { errorMapper.mapError(badResponseException) } returns expectedResult

        //Act
        aboutAmiiboViewModel.getInfoAbout(AMIIBO_TAIL, true)

        //Assert
        verifySequence {
            showProgressBarObserver.onChanged(true)
            showProgressBarObserver.onChanged(false)
            errorObserver.onChanged(expectedResult)
        }
        verify { amiiboObserver wasNot Called }
    }

    companion object {
        private const val AMIIBO_TAIL = "000e0002"

        private val AMIIBO_INFO_EXPECTED_RESPONSE = Amiibo(listOf(
            AmiiboModel(
                amiiboSeries = "Super Smash Bros.",
                gameSeries = "The Legend of Zelda",
                image = "https://raw.githubusercontent.com/N3evin/AmiiboAPI/master/images/icon_01010000-000e0002.png",
                name = "Zelda",
                type = "Figure")
        ))

        private val EXCEPTED_ERROR_CONNECTION_LOST = Error(R.string.unknown_host_message)
        private val EXPECTED_ERROR_CONNECTION_TIMEOUT = Error(R.string.connection_timeout)
        private const val NOT_FOUND = 404
        private val EXPECTED_ERROR_NOT_FOUND = Error(R.string.not_found)
    }
}