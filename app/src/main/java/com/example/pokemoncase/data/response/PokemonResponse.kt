package com.example.pokemoncase.data.response

import com.example.pokemoncase.data.model.PokemonModel
import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("count")
    val count:Int,
    @SerializedName("next")
    val next:String,
    @SerializedName("previous")
    val previous:String,
    @SerializedName("results")
    val results:ArrayList<PokemonModel>)