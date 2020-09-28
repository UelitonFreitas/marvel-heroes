package com.hero.marvelheroes.mainscreen.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hero.marvelheroes.R
import com.hero.marvelheroes.repository.Character
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val characters: List<Character>,
    private val onCharacterClick: (Character) -> Unit
) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    inner class CharacterViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        private val characterName by lazy { layout.findViewById<TextView>(R.id.text_view_character_name) }
        private val characterImage by lazy { layout.findViewById<ImageView>(R.id.image_view_character_image) }

        fun setCharacter(character: Character) {
            characterName.text = character.name

            characterImage.run {
                Picasso.get()
                    .load(character.imageUrl)
                    .placeholder(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
                    .resize(50, 50)
                    .centerCrop()
                    .into(this)
            }

            layout.setOnClickListener {
                onCharacterClick(characters[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item_list, parent, false) as ConstraintLayout

        return CharacterViewHolder(layout)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setCharacter(characters[position])
    }
}