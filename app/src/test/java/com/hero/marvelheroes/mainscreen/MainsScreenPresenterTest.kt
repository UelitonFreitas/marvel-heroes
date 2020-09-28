package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class MainsScreenPresenterTest {

    @MockK
    private lateinit var mainScreenView: MainScreenProtocols.View

    @MockK
    private lateinit var charactersRepository: CharactersRepository

    private lateinit var mainScreenPresenter: MainScreenPresenter

    private val pageZeroExpectedCharacters =
        listOf(
            Character("1", "Spider Man", "spider"),
            Character("2", "Iron Man", "iron"),
            Character("3", "Black Widow", "black")
        )

    private val pageTwoExpectedCharacters =
        listOf(Character("4", "Thanos", "thanos"), Character("5", "Agent Brand", "brand"))

    private val limitOffSet = 20

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        mainScreenPresenter = MainScreenPresenter(mainScreenView, charactersRepository)
    }

    @Test
    fun `should list all characters`() {
        assertInitialCharacterList()
    }

    @Test
    fun `should show empty characters list when there is now characters`() {
        val expectedCharacters = emptyList<Character>()

        returnFromRepository(0, limitOffSet, expectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify { mainScreenView.showEmptyList() }
    }


    @Test
    fun `should show error message when there was an error when loading characters`() {
        returnFromRepositoryWithError()

        mainScreenPresenter.getCharactersList()

        verify { mainScreenView.showErrorMessage() }
    }

    @Test
    fun `should show next characters page`() {
        assertInitialCharacterList()

        assertPageOneCharacters()
    }

    @Test
    fun `should show loading on view when fetching characters`() {
        returnFromRepository(0, limitOffSet, pageZeroExpectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify(exactly = 1) { mainScreenView.showLoading() }
    }

    @Test
    fun `should hide loading on view when fetching characters`() {
        returnFromRepository(0, limitOffSet, pageZeroExpectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify(atLeast = 1) { mainScreenView.hideLoading() }
    }

    @Test
    fun `should hide loading on view when was an error`() {
        returnFromRepositoryWithError()

        mainScreenPresenter.getCharactersList()

        verify(atLeast = 1) { mainScreenView.hideLoading() }
    }

    private fun assertPageOneCharacters() {
        returnFromRepository(20, limitOffSet, pageTwoExpectedCharacters)

        mainScreenPresenter.loadNextCharactersOffset()

        verify(atLeast = 1) { mainScreenView.showCharacters(eq(pageZeroExpectedCharacters + pageTwoExpectedCharacters)) }
    }

    private fun assertInitialCharacterList() {
        returnFromRepository(0, limitOffSet, pageZeroExpectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify { mainScreenView.showCharacters(eq(pageZeroExpectedCharacters)) }
    }

    private fun returnFromRepository(offSet: Int, limit: Int, expectedCharacters: List<Character>) {
        slot<((List<Character>) -> Unit)>().let { callback ->
            every {
                charactersRepository.getCharactersList(
                    offSet = offSet,
                    limit = limit,
                    onError = any(),
                    onSuccess = capture(callback)
                )
            } answers {
                callback.captured.invoke(expectedCharacters)
            }
        }
    }

    private fun returnFromRepositoryWithError() {
        slot<(() -> Unit)>().let { callback ->
            every {
                charactersRepository.getCharactersList(
                    offSet = 0,
                    limit = limitOffSet,
                    onError = capture(callback),
                    onSuccess = any()
                )
            } answers {
                callback.captured.invoke()
            }
        }
    }
}