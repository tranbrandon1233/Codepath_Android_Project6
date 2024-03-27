package com.example.project6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PokemonAdapter (private val pokemonList: List<MutableList<String>>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val pokemonImg: ImageView
            val name : TextView
            val abilities : TextView
            init {
                // Find our RecyclerView item's ImageView for future use
                pokemonImg = view.findViewById(R.id.pokemon_image)
                name = view.findViewById(R.id.name)
                abilities = view.findViewById(R.id.abilities)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // Create a new view, which defines the UI of the list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_pokemon, parent, false)

            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            var ind = 0
            holder.name.text = pokemonList[position][1].toString()
            val name = pokemonList[position][1].toString()
            holder.abilities.text = pokemonList[position][2].toString()
            holder.pokemonImg.setOnClickListener {
                Toast.makeText(holder.itemView.context, "You chose $name!", Toast.LENGTH_SHORT).show()
                ind = if(ind == 0){
                    3
                } else{
                    0
                }
                Glide.with(holder.itemView)
                    .load(pokemonList[position][ind])
                    .centerCrop()
                    .into(holder.pokemonImg)
            }
            Glide.with(holder.itemView)
                .load(pokemonList[position][ind])
                .centerCrop()
                .into(holder.pokemonImg)
        }

        override fun getItemCount() = pokemonList.size

}