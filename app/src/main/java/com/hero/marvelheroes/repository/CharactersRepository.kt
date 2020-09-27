package com.hero.marvelheroes.repository

interface CharactersRepository {
    fun getCharactersList(onError: (() -> Unit)? = null, onSuccess: (List<Character>) -> Unit)
}