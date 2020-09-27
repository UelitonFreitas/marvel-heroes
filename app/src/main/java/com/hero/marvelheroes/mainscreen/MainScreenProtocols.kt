package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.repository.Character

interface MainScreenProtocols {

    interface View {
        fun showCharacters(characters: List<Character>)
        fun showEmptyList()
    }

    interface Presenter {
        fun getCharactersList()
    }
}