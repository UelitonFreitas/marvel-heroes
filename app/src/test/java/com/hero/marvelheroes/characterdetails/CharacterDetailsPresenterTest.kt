package com.hero.marvelheroes.characterdetails

import com.hero.marvelheroes.characterdetailscreen.CharacterDetailsPresenter
import com.hero.marvelheroes.characterdetailscreen.CharacterDetailsProtocol
import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CharacterDetailsPresenterTest {

    @MockK
    private lateinit var characterDetailsView: CharacterDetailsProtocol.View

    @MockK
    private lateinit var charactersRepository: CharactersRepository

    private lateinit var characterDetailsPresenter: CharacterDetailsProtocol.Presenter

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)

        characterDetailsPresenter = CharacterDetailsPresenter(characterDetailsView, charactersRepository)
    }

    @Test
    fun `should show character details`() {
        val expectedCharacter = Character("aId", "Spider Man", "aUrl")

        slot<((Character) -> Unit)>().let { callback ->
            every {
                charactersRepository.getCharacter(
                    expectedCharacter.id,
                    onError = any(),
                    onSuccess = capture(callback)
                )
            } answers {
                callback.captured.invoke(expectedCharacter)
            }
        }

        characterDetailsPresenter.loadCharacter(expectedCharacter.id)

        verify { characterDetailsView.showCharacter(eq(expectedCharacter)) }
    }

    @Test
    fun `should show error message when there was an error on load character`() {
        val expectedCharacter = Character("aId", "Spider Man", "aUrl")

        slot<(() -> Unit)>().let { callback ->
            every {
                charactersRepository.getCharacter(
                    expectedCharacter.id,
                    onError = capture(callback),
                    onSuccess = any()
                )
            } answers {
                callback.captured.invoke()
            }
        }

        characterDetailsPresenter.loadCharacter(expectedCharacter.id)

        verify { characterDetailsView.showErrorMessage() }
    }
}