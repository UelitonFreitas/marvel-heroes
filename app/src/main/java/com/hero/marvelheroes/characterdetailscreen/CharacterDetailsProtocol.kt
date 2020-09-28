package com.hero.marvelheroes.characterdetailscreen

import com.hero.marvelheroes.repository.Character

interface CharacterDetailsProtocol {
    interface View {
        fun showCharacter(character: Character)
    }

    interface Presenter {
        fun loadCharacter(character: Character)
    }
}