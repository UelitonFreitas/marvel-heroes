package com.hero.marvelheroes.repository

interface CharactersRepository {
    fun getCharactersList(offSet: Int, limit: Int, onError: (() -> Unit)? = null, onSuccess: (List<Character>) -> Unit)
}