package com.example.pokemoncase.data.model

import com.google.gson.annotations.SerializedName

data class PokemonImageModel (
    @SerializedName("front_default")
    val frontDefault:String,
    @SerializedName("back_default")
    val backDefault:String,
        )