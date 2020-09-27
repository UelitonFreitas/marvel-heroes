package com.hero.marvelheroes.mainscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.hero.marvelheroes.R
import com.hero.marvelheroes.repository.Character
import com.squareup.picasso.Picasso

class CharactersAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun setCharacter(character: Character) {
            with(layout) {
                findViewById<TextView>(R.id.text_view_character_name).text = character.name

                findViewById<ImageView>(R.id.image_view_character_image).run {
                    Picasso.get()
                        .load(character.imageUrl)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .resize(50, 50)
                        .centerCrop()
                        .into(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.character_item_list, parent, false) as ConstraintLayout

        return CharacterViewHolder(textView)
    }

    override fun getItemCount() = characters.size

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.setCharacter(characters[position])
    }
}