package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.model.Character
import com.hero.marvelheroes.model.CharactersRepository
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

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        mainScreenPresenter = MainScreenPresenter(mainScreenView, charactersRepository)
    }

    @Test
    fun `should list all characters`() {
        val expectedCharacters =
            listOf(Character("Spider Mans"), Character("Iron Man"), Character("Black Widow"))

        returnFromRepository(expectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify { mainScreenView.showCharacters(eq(expectedCharacters)) }
    }

    @Test
    fun `should show empty characters list when there is now characters`() {

        val expectedCharacters = emptyList<Character>()

        returnFromRepository(expectedCharacters)

        mainScreenPresenter.getCharactersList()

        verify { mainScreenView.showEmptyList() }
    }

    private fun returnFromRepository(expectedCharacters: List<Character>) {
        slot<((List<Character>) -> Unit)>().let { callback ->
            every {
                charactersRepository.getCharactersList(
                    onError = any(),
                    onSuccess = capture(callback)
                )
            } answers {
                callback.captured.invoke(expectedCharacters)
            }
        }
    }
}