package com.hero.marvelheroes.characterdetailscreen

import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository

class CharacterDetailsPresenter(
    private val characterDetailsView: CharacterDetailsProtocol.View
) : CharacterDetailsProtocol.Presenter {

    override fun loadCharacter(character: Character) {
        characterDetailsView.showCharacter(character)
    }
}