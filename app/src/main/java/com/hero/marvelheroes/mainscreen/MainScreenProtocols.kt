package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.repository.Character

interface MainScreenProtocols {

    interface View {
        fun showCharacters(characters: List<Character>)
        fun showEmptyList()
        fun showLoading()
        fun hideLoading()
        fun showErrorMessage()
    }

    interface Presenter {
        fun getCharactersList()
        fun loadNextCharactersOffset()
    }
}