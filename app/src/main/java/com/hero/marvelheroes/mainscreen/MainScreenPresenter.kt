package com.hero.marvelheroes.mainscreen

import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.CharactersRepository

class MainScreenPresenter(
    private val view: MainScreenProtocols.View,
    private val charactersRepository: CharactersRepository
) :
    MainScreenProtocols.Presenter {

    private var actualOffSet = 0
    private val limitOffSet = 20
    private val characters = mutableListOf<Character>()

    override fun getCharactersList() {
        view.showLoading()
        charactersRepository.getCharactersList(
            actualOffSet,
            limit = limitOffSet,
            onError = ::showErrorMessage,
            onSuccess = ::showCharacters
        )
    }

    private fun showCharacters(characters: List<Character>) {
        view.hideLoading()

        with(this.characters){
            addAll(characters)
            takeIf { it.isNotEmpty() }?.let {
                view.showCharacters(this)
            } ?: view.showEmptyList()
        }
    }

    override fun loadNextCharactersOffset() {
        actualOffSet += limitOffSet
        getCharactersList()
    }

    override fun onCharacterSelected(character: Character) {
        view.goToCharacterDetails(character)
    }

    private fun showErrorMessage(t: Throwable){
        view.hideLoading()
        view.showErrorMessage()
    }
}