package com.example.githubclient

import com.example.githubclient.mvp.presenter.MainPresenter
import com.example.githubclient.mvp.view.MainView
import com.example.githubclient.navigation.IScreens
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import com.nhaarman.mockito_kotlin.atLeastOnce
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    private lateinit var presenter: MainPresenter

    @Mock
    private lateinit var view: MainView

    @Mock
    private lateinit var router: Router

    @Mock
    private lateinit var screens: IScreens

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        presenter = MainPresenter()
        presenter.router = router
        presenter.screens = screens
        presenter.attachView(view)

    }

    @Test
    fun onFirstViewAttach_Test() {
        val expectedScreen = mock<Screen>()
        whenever(screens.users()).thenReturn(expectedScreen)

        presenter.onFirstViewAttach()

        Mockito.verify(screens, atLeastOnce()).users()
        Mockito.verify(router, times(1)).replaceScreen(expectedScreen)
    }

    @Test
    fun backClicked_shouldExitRouter() {

        presenter.backClicked()

        Mockito.verify(router, times(1)).exit()
    }
}