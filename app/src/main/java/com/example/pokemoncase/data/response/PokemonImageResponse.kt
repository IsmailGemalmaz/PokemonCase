package com.example.pokemoncase.data.response

import com.example.pokemoncase.data.model.PokemonImageModel
import com.example.pokemoncase.data.model.PokemonModel
import com.google.gson.annotations.SerializedName
import java.util.*

data class PokemonImageResponse (
    @SerializedName("sprites")
    val sprites:PokemonImageModel,
        )