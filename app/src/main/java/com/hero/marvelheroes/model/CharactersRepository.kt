package com.hero.marvelheroes.model

interface CharactersRepository {
    fun getCharactersList(onError: (() -> Unit)? = null, onSuccess: (List<Character>) -> Unit)
}