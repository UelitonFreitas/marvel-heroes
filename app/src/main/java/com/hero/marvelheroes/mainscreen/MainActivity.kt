package com.hero.marvelheroes.mainscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hero.marvelheroes.R
import com.hero.marvelheroes.mainscreen.adapters.CharactersAdapter
import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.remote.RemoteCharacterRepository

class MainActivity : AppCompatActivity(), MainScreenProtocols.View {

    private val charactersList by lazy { findViewById<RecyclerView>(R.id.recycler_view_characters) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
    }

    override fun onResume() {
        super.onResume()

        val presenter = MainScreenPresenter(this, RemoteCharacterRepository())
        presenter.getCharactersList()
    }

    override fun showCharacters(characters: List<Character>) {

        val viewAdapter = CharactersAdapter(characters)
        val viewManager = LinearLayoutManager(this)

        charactersList.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun showEmptyList() {
        Toast.makeText(this, "Empty List", Toast.LENGTH_SHORT)
    }
}