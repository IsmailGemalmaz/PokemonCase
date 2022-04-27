package com.example.pokemoncase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pokemoncase.data.model.PokemonDetailModel
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.data.repository.PokemonRepository
import com.example.pokemoncase.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonsDetailViewModel @Inject constructor(private val repository: PokemonRepository) : BaseViewModel() {

    val pokemonName= MutableLiveData<String>()
    val pokemonFrontImage= MutableLiveData<String>()
    val pokemonBackImage= MutableLiveData<String>()
    val pokemonWeight= MutableLiveData<Int>()
    val pokemonHeight= MutableLiveData<Int>()
    val pokemonsLoading=MutableLiveData<Boolean>()
    lateinit var imgUrl:String

    fun getPokemonDetail(url:String){
        viewModelScope.launch {
            pokemonsLoading.value=true
            repository.getPokemonDetail(url).let {
                pokemonsLoading.value=false
                    pokemonName.postValue(it.forms[0].name)
                    imgUrl=it.forms[0].url
                    pokemonWeight.postValue(it.weight)
                    pokemonHeight.postValue(it.height)
                getPokemonImage()
            }
        }
    }

    fun getPokemonImage(){
        viewModelScope.launch {
            repository.getPokemonImage(imgUrl).let {
                pokemonFrontImage.postValue(it.sprites.frontDefault)
                pokemonBackImage.postValue(it.sprites.backDefault)
               Log.e("İMAGEEEEE HOPPPP",it.sprites.frontDefault)
            }
        }
    }
}