package com.paiva.marvel.screens.heroDetails

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.paiva.marvel.R
import com.paiva.marvel.model.Result
import kotlinx.android.synthetic.main.bottom_sheet_hero_details.*

class HeroDetailsBottomSheet(var hero: Result): BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_hero_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populateTexts()
        populateListComics(view.context)
    }

    private fun populateListComics(context: Context) {
        recyclerViewHeroComics.layoutManager = LinearLayoutManager(context)
        recyclerViewHeroComics.adapter = HeroDetailsBottomSheetAdapter(hero.comics.items)
    }

    private fun populateTexts() {
        textViewHeroName.text = hero.name
        textViewHeroDescription.text = getHeroDescription(hero)
    }

    private fun getHeroDescription(hero: Result): String {
        val isBlankDescription = hero.description == EMPTY_STRING
        return if (isBlankDescription) {
            getString(R.string.hero_details_bottomsheet_dont_have_description)
        } else {
            hero.description
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    companion object {
        const val EMPTY_STRING = ""
    }
}