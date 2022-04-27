package com.example.pokemoncase.network

import com.example.pokemoncase.data.response.PokemonImageResponse
import com.example.pokemoncase.data.response.PokemonResponse
import com.example.pokemoncase.data.response.PokemonsDetailResponse
import retrofit2.http.GET
import retrofit2.http.Url

interface PokemonService {

    @GET
    suspend fun getPokemon(
        @Url url:String
    ): PokemonResponse


    @GET
    suspend fun getPokemonDetail(
        @Url url:String
    ): PokemonsDetailResponse

    @GET
    suspend fun getPokemonImage(
        @Url url:String
    ): PokemonImageResponse


}