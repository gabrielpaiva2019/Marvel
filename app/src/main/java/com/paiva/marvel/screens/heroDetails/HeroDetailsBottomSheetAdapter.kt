package com.paiva.marvel.screens.heroDetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paiva.marvel.R
import com.paiva.marvel.model.ComicsItem

class HeroDetailsBottomSheetAdapter(var comicList: List<ComicsItem>): RecyclerView.Adapter<HeroDetailsBottomSheetAdapter.ComicsAdapter>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicsAdapter {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comics_item, parent, false)
        return ComicsAdapter(view)
    }

    override fun onBindViewHolder(holder: ComicsAdapter, position: Int) {
        val comicItem = comicList[position]

        holder.textViewComicTitle.text = comicItem.name
    }

    override fun getItemCount(): Int {
       return comicList.size
    }

    class ComicsAdapter(view: View): RecyclerView.ViewHolder(view) {
        val textViewComicTitle: TextView = view.findViewById(R.id.textViewComicTitle)
    }

}