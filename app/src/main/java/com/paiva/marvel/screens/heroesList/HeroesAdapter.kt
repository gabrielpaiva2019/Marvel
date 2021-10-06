package com.paiva.marvel.screens.heroesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.paiva.marvel.R
import com.paiva.marvel.model.Result
import com.squareup.picasso.Picasso

class HeroesAdapter(var heroesList: List<Result>): RecyclerView.Adapter<HeroesAdapter.HeroesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heroes_item, parent, false)
        return HeroesViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroesViewHolder, position: Int) {
        val hero: Result = heroesList[position]
        val thumbnailExtension = hero.thumbnail.extension
        val thumbnailPath = hero.thumbnail.path + "/portrait_xlarge.$thumbnailExtension"

        Picasso.get()
            .load(thumbnailPath)
            .into(holder.imageHero)
    }

    override fun getItemCount(): Int {
        return heroesList.size
    }

    class HeroesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imageHero = view.findViewById<ImageView>(R.id.imageViewHero)
    }
}