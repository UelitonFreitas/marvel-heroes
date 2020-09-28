package com.hero.marvelheroes.repository

interface CharactersRepository {
    fun getCharactersList(offSet: Int, limit: Int, onError: ((Throwable) -> Unit)? = null, onSuccess: (List<Character>) -> Unit)
}