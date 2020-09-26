package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.model.Character

interface MainScreenProtocols {

    interface View {
        fun showCharacters(characters: List<Character>)
        fun showEmptyList()
    }

    interface Presenter {
        fun getCharactersList()
    }
}