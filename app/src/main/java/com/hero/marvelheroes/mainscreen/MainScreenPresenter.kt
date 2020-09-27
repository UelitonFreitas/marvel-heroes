package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository

class MainScreenPresenter(
    private val view: MainScreenProtocols.View,
    private val charactersRepository: CharactersRepository
) :
    MainScreenProtocols.Presenter {

    override fun getCharactersList() {

        charactersRepository.getCharactersList(onSuccess = ::showCharacters)
    }

    private fun showCharacters(characters: List<Character>) {

        characters.takeIf { it.isNotEmpty() }?.let {
            view.showCharacters(characters)
        } ?: view.showEmptyList()
    }
}