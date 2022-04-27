package com.example.pokemoncase.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemoncase.constant.ApiEndpoint
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.data.repository.PokemonRepository
import com.example.pokemoncase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.ArrayList
import javax.inject.Inject

@HiltViewModel
class PokemonsViewModel @Inject constructor(private val repository: PokemonRepository) :
    BaseViewModel() {

    val pokemon= MutableLiveData<List<PokemonModel>?>()
    val pokemonsLoading=MutableLiveData<Boolean>()

    var pokemonNext:String=ApiEndpoint.POKEMON_LIST
    var list:MutableList<PokemonModel>?= ArrayList()

    fun getPokemon(){
            pokemonsLoading.value=true
            viewModelScope.launch {
                repository.getPokemon(pokemonNext).let {
                    pokemonsLoading.value=false
                    pokemonNext=it.next
                    pokemon.postValue(it.results)

            }
        }
    }
}