package com.hero.marvelheroes.characterdetails

import com.hero.marvelheroes.characterdetailscreen.CharacterDetailsPresenter
import com.hero.marvelheroes.characterdetailscreen.CharacterDetailsProtocol
import com.hero.marvelheroes.repository.Character
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class CharacterDetailsPresenterTest {

    @MockK
    private lateinit var characterDetailsView: CharacterDetailsProtocol.View


    private lateinit var characterDetailsPresenter: CharacterDetailsProtocol.Presenter

    @Before
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)

        characterDetailsPresenter = CharacterDetailsPresenter(characterDetailsView)
    }

    @Test
    fun `should show character details`() {
        val expectedCharacter = Character("aId", "Spider Man", "aUrl")

        characterDetailsPresenter.loadCharacter(expectedCharacter)

        verify { characterDetailsView.showCharacter(eq(expectedCharacter)) }
    }
}