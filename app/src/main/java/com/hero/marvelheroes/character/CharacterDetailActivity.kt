package com.hero.marvelheroes.character

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.hero.marvelheroes.R
import com.hero.marvelheroes.repository.Character

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

        supportActionBar?.title = "Detail"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    companion object {

        private const val characterIdKey = "characterId"
        fun getIntent(context: Context, character: Character) =
            Intent(context, CharacterDetailActivity::class.java).apply {
                Bundle().apply { putString(characterIdKey, character.id) }.also {
                    putExtras(it)
                }
            }
    }
}