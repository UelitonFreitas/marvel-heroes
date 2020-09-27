package com.hero.marvelheroes.mainscreen.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.hero.marvelheroes.R
import com.hero.marvelheroes.repository.Character

class CharactersAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val layout: ConstraintLayout) :
        RecyclerView.ViewHolder(layout) {

        fun setCharacter(character: Character) {
            layout.findViewById<TextView>(R.id.text_view_character_name).text = character.name
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