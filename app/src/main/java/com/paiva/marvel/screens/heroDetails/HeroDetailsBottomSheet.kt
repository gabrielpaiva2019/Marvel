package com.paiva.marvel.screens.heroDetails

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun populateTexts() {
        textViewHeroName.text = hero.name
        textViewHeroDescription.text = hero.description
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
    }

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }
}