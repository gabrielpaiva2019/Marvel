package com.paiva.marvel.screens.heroesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.paiva.marvel.model.Result
import com.paiva.marvel.service.MarvelService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.net.InetAddress

class HeroesViewModel(private var service: MarvelService): ViewModel() {

    private val _error = MutableLiveData<Boolean>()

    private val _heroesWithoutCarroselItens = MutableLiveData<List<Result>>()
    private val _heroesCarroselItens = MutableLiveData<List<Result>>()

    val heroesWithoutCarroselItens: LiveData<List<Result>>
        get() = _heroesWithoutCarroselItens

    val heroesCarroselItens: LiveData<List<Result>>
        get() = _heroesCarroselItens

    val error: LiveData<Boolean>
        get() = _error

    lateinit var heroesFilteredList: ArrayList<Result>
    lateinit var carrosselList: ArrayList<Result>

    fun fetchHeroes() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isInternetAvailable()){
                val response = service.getHeroes()
                if (response != null) {
                    withContext(Dispatchers.Main) {
                        val hero = response.data?.results

                        heroesFilteredList = ArrayList(hero?.subList(CARROSSEL_LIMIT_INDEX, hero.size))
                        carrosselList = ArrayList(hero?.subList(FIRST_ITEM_ARRAY, CARROSSEL_LIMIT_INDEX))

                        _heroesCarroselItens.value = carrosselList
                        _heroesWithoutCarroselItens.value = heroesFilteredList
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _error.value = true
                    }
                }
            } else {
                withContext(Dispatchers.Main) {
                    _error.value = true
                }
            }
        }
    }

    private fun isInternetAvailable(): Boolean {
        return try {
            val inetAddress = InetAddress.getByName(GOOGLE_HOST)
            !inetAddress.equals(EMPTY_STRING)
        } catch (e: Exception) {
            false
        }
    }

    companion object {
        const val FIRST_ITEM_ARRAY = 0
        const val CARROSSEL_LIMIT_INDEX = 5
        const val GOOGLE_HOST = "google.com"
        const val EMPTY_STRING = ""
    }
}