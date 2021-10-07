package com.paiva.marvel.screens.heroesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.paiva.marvel.R
import com.paiva.marvel.model.Result
import com.squareup.picasso.Picasso

class HeroesViewPagerAdapter(var heroesList: List<Result>):
    RecyclerView.Adapter<HeroesViewPagerAdapter.HeroesViewPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroesViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.heroes_viewpager_item, parent, false)
        return HeroesViewPagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: HeroesViewPagerViewHolder, position: Int) {
        val hero: Result = heroesList[position]
        val thumbnailExtension = hero.thumbnail.extension
        val thumbnailPath = hero.thumbnail.path + "/landscape_amazing.$thumbnailExtension"

        Picasso.get()
            .load(thumbnailPath)
            .into(holder.imageHero)
    }

    override fun getItemCount(): Int {
       return 5
    }

    class HeroesViewPagerViewHolder(view: View): RecyclerView.ViewHolder(view){
        val imageHero = view.findViewById<ImageView>(R.id.imageViewHero)
    }

}