package com.example.pokemoncase.ui

import android.view.View
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.ui.viewmodel.PokemonViewModel

interface PokemonClickListener {
    fun onPokemonClicked(position:Int)
}