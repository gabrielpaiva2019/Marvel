package com.paiva.marvel.screens.heroesList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paiva.marvel.model.Heroes
import com.paiva.marvel.service.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HeroesViewModel: ViewModel() {
    private val service = MarvelService()
    private val _heroes = MutableLiveData<Heroes>()

    val heroes: LiveData<Heroes>
        get() = _heroes

    fun fetchHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = service.getHeroes()

            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _heroes.value = response.body()
                }
            }
        }
    }
}