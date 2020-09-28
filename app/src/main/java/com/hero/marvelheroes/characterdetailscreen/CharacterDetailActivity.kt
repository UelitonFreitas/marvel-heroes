package com.hero.marvelheroes.characterdetailscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.hero.marvelheroes.R
import com.hero.marvelheroes.repository.Character
import com.squareup.picasso.Picasso

class CharacterDetailActivity : AppCompatActivity(), CharacterDetailsProtocol.View {

    private val characterName by lazy { findViewById<TextView>(R.id.text_view_character_name) }
    private val characterImage by lazy { findViewById<ImageView>(R.id.image_view_character_image) }
    val character: Character? get() = intent.extras?.getParcelable(characterIdKey)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_detail)

        setupToolbar()
    }

    private fun setupToolbar() {
        val toolBar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        CharacterDetailsPresenter(this).loadCharacter(this.character!!)
    }

    override fun showCharacter(character: Character) {
        supportActionBar?.title = character.name

        characterName.text = character.name

        Picasso.get()
            .load(character.imageUrl)
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .resize(150, 150)
            .centerCrop()
            .into(characterImage)
    }

    companion object {

        private const val characterIdKey = "characterId"

        fun getIntent(context: Context, character: Character) =
            Intent(context, CharacterDetailActivity::class.java).apply {
                Bundle().apply { putExtra(characterIdKey, character) }.also {
                    putExtras(it)
                }
            }
    }
}