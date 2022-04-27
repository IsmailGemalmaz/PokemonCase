package com.example.pokemoncase.ui.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.pokemoncase.R
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemoncase.data.model.PokemonModel
import com.example.pokemoncase.databinding.ItemPokemonBinding
import com.example.pokemoncase.ui.PokemonClickListener
import com.example.pokemoncase.ui.activity.PokemonsDetailActivity
import javax.inject.Inject

class PokemonAdapter @Inject constructor() :
   RecyclerView.Adapter<PokemonViewHolder>(), PokemonClickListener{
     var pokemons:ArrayList<PokemonModel> = ArrayList()
    lateinit var context:Context
    var index:Int?=null
  lateinit var  mListener:OnItemClickListener

   interface OnItemClickListener{
       fun onClick(position:Int)
   }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater=LayoutInflater.from(parent.context)
        val view=DataBindingUtil.inflate<ItemPokemonBinding>(inflater,R.layout.item_pokemon,parent,false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
       holder.view.pokemonItemUiState=pokemons[position]
        holder.view.listener=this
        holder.view.position=position;
    }

    override fun getItemCount(): Int {
        return pokemons.size
    }

    override fun onPokemonClicked(position: Int) {
        val intent = Intent(context, PokemonsDetailActivity::class.java)
        val pokemon = pokemons.get(position);
        intent.putExtra("pokemonUrl",pokemon.url )
        context.startActivity(intent)

    }
}