package com.paiva.marvel.screens.heroesList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.paiva.marvel.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HeroesActivity : AppCompatActivity() {
    private val viewModel by viewModel<HeroesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configScreen()
    }

    private fun configScreen() {
        setupObservers()
        viewModel.fetchHeroes()
    }

    private fun setupObservers() {
        viewModel.error.observe(this, Observer {
            if(it == true){
                Toast.makeText(this@HeroesActivity, "ERRO", Toast.LENGTH_LONG).show()
            }else {

            }
        })

        viewModel.heroes.observe(this@HeroesActivity, Observer { heroes ->
            var gridLayoutManager = GridLayoutManager(this, 3)
            val adapter = HeroesAdapter(heroes.data.results)
            recyclerListHeroes.layoutManager = gridLayoutManager
            recyclerListHeroes.addItemDecoration(HeroesRecyclerViewDecoration(3, 50, true))
            recyclerListHeroes.adapter = adapter

        })
    }
}