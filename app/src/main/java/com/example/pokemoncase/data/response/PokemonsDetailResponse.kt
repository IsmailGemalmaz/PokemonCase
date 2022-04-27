package com.example.pokemoncase.data.response

import com.example.pokemoncase.data.model.PokemonDetailModel
import com.google.gson.annotations.SerializedName

data class PokemonsDetailResponse (
    @SerializedName("height")
    val height:Int,
    @SerializedName("weight")
    val weight:Int,
    @SerializedName("forms")
    val forms:ArrayList<PokemonDetailModel>
        )