package com.example.pokemoncase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.data.repository.PokemonRepository
import com.example.pokemoncase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel @Inject constructor(private val repository:PokemonRepository) : BaseViewModel() {

   val pokemon= MutableLiveData<List<PokemonModel>?>()
    var pokemonNext:String="pokemon?limit=20&offset=0"
    var list:MutableList<PokemonModel>?=ArrayList()

    fun getPokemon(){

        viewModelScope.launch {
            repository.getPokemon(pokemonNext).let {
               list=it.results
                pokemonNext=it.next
                Log.e("hopppp",it.results.toString())
                pokemon.postValue(it.results)
            }
        }
        pokemon.value=list
    }



}
