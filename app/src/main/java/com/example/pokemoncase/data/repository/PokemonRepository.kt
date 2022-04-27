package com.example.pokemoncase.data.repository

import androidx.paging.PagingData
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.network.PokemonService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


class PokemonRepository @Inject constructor(private  val apiService: PokemonService){
 suspend fun getPokemon(url: String)=apiService.getPokemon(url)

 suspend fun getPokemonDetail(url:String)=apiService.getPokemonDetail(url)

 suspend fun getPokemonImage(url:String)=apiService.getPokemonImage(url)

}