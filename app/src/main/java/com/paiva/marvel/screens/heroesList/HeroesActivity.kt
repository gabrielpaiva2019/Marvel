package com.paiva.marvel.screens.heroesList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.paiva.marvel.R

class HeroesActivity : AppCompatActivity() {
    lateinit var viewModel: HeroesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)
            .get(HeroesViewModel::class.java)

        viewModel.fetchHeroes()
    }
}