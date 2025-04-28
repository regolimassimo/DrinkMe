package com.massimoregoli.drinkme.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
    private val repo = CocktailRepository(application)

    private val _cocktails = MutableLiveData<List<SmallCocktail>>()
    val cocktails: LiveData<List<SmallCocktail>> = _cocktails

    private val _drinks = MutableLiveData<List<LargeCocktail>>()
    val drinks: LiveData<List<LargeCocktail>> = _drinks

    init {
        loadCocktails("margarita")
    }

    fun loadCocktails(filter: String = "") {
        if (filter.isBlank()) {
            _cocktails.value = emptyList()
            return
        }
        repo.fetchCocktails(filter) {
            _cocktails.value = it ?: emptyList()
        }
    }
    fun removeDrink() {
        _drinks.value = emptyList()
    }
    fun loadDrinks(id: Int) {
        repo.fetchCocktail(id) {
            _drinks.value = it ?: emptyList()
        }
    }
}

class CocktailViewModelFactory(private val application: Application) :
    ViewModelProvider.AndroidViewModelFactory(application) {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CocktailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CocktailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
