package com.hero.marvelheroes.mainscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.hero.marvelheroes.R
import com.hero.marvelheroes.character.CharacterDetailActivity
import com.hero.marvelheroes.mainscreen.adapters.CharactersAdapter
import com.hero.marvelheroes.repository.Character
import com.hero.marvelheroes.repository.remote.RemoteCharacterRepository

class MainActivity : AppCompatActivity(), MainScreenProtocols.View {

    private val charactersList by lazy { findViewById<RecyclerView>(R.id.recycler_view_characters) }
    private val swipeLayout by lazy { findViewById<SwipeRefreshLayout>(R.id.swipe_container) }
    private lateinit var presenter: MainScreenPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))

        presenter = MainScreenPresenter(this, RemoteCharacterRepository())

        swipeLayout.setOnRefreshListener {
            presenter.getCharactersList()
        }

        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light);
    }

    override fun onResume() {
        super.onResume()

        presenter.getCharactersList()
    }

    override fun showCharacters(characters: List<Character>) {
        val viewAdapter = CharactersAdapter(characters, onCharacterClick = ::onCharacterClick)
        val viewManager = LinearLayoutManager(this)

        charactersList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        charactersList.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val canScrollVerticallyFromTopToBottom = recyclerView.canScrollVertically(1)

                if(!canScrollVerticallyFromTopToBottom) {
                    presenter.loadNextCharactersOffset()
                }
            }
        })
    }

    private fun onCharacterClick(character: Character) {
        presenter.onCharacterSelected(character)
    }

    override fun showEmptyList() {
        Toast.makeText(this, "Empty List", Toast.LENGTH_SHORT)
    }

    override fun showLoading() {
        swipeLayout.isRefreshing = true
    }

    override fun hideLoading() {
        swipeLayout.isRefreshing = false
    }

    override fun showErrorMessage() {
        Toast.makeText(this, "Sorry! Error to get characters", Toast.LENGTH_SHORT)
    }

    override fun goToCharacterDetails(character: Character) {
        startActivity(CharacterDetailActivity.getIntent(this, character))
    }
}