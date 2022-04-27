package com.example.pokemoncase.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.NavUtils
import com.example.pokemoncase.R
import com.example.pokemoncase.constant.IntentKey
import com.example.pokemoncase.databinding.ActivityPokemonsDetailBinding
import com.example.pokemoncase.service.OverlayService
import com.example.pokemoncase.ui.base.BaseActivity
import com.example.pokemoncase.ui.viewmodel.PokemonsDetailViewModel
import com.example.pokemoncase.util.downloadFromUrl
import com.example.pokemoncase.util.placeHolderProgresBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonsDetailActivity : BaseActivity<ActivityPokemonsDetailBinding>() {

    private val viewModel: PokemonsDetailViewModel by viewModels()
     private lateinit var pokemonName:String
    private lateinit var pokemonBackImg:String
    private lateinit var pokemonFrontImg:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        funIntialize()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun funIntialize() {
        observeLiveData()
        setListener()
        val url: String = intent.getSerializableExtra("pokemonUrl") as String
        viewModel.getPokemonDetail(url)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val intent = Intent(this@PokemonsDetailActivity, MainActivity::class.java)
            NavUtils.navigateUpTo(this@PokemonsDetailActivity, intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_pokemons_detail
    }

    private fun setListener() {
        viewBinding.btnOverlay.setOnClickListener { startOverlayService() }
    }

    private fun observeLiveData() {
        viewModel.pokemonName.observe(this, { pokemonName ->
            pokemonName?.let {
                viewBinding.tvPokemonName.setText("Pokemon Name : ${it}")
                viewBinding.btnOverlay.setText("SHOW ${it} IN OVERLAY")
                this.pokemonName=it
            }
        })
        viewModel.pokemonHeight.observe(this, { pokemonHeight ->
            pokemonHeight?.let {
                viewBinding.tvPokemonHeight.setText("Pokemon Height : ${it}")
            }
        })
        viewModel.pokemonWeight.observe(this, { pokemonWeight ->
            pokemonWeight?.let {
                viewBinding.tvPokemonWeight.setText("Pokemon Weight : ${it}")
            }
        })
        viewModel.pokemonsLoading.observe(this, { loading ->
            loading?.let {
                if (it) {
                    viewBinding.pokemonsLoading.visibility = View.VISIBLE
                } else {
                    viewBinding.pokemonsLoading.visibility = View.GONE
                }
            }
        })
        viewModel.pokemonFrontImage.observe(this, { pokemonImage ->
            pokemonImage?.let {
                this?.let {
                    this.pokemonFrontImg=pokemonImage
                    viewBinding.ivPokemon.downloadFromUrl(pokemonImage, placeHolderProgresBar(it))
                }
            }
        })
        viewModel.pokemonBackImage.observe(this, { pokemonImage ->
            pokemonImage?.let {
                this?.let {
                    this.pokemonBackImg=pokemonImage
                }
            }
        })
    }

    fun startOverlayService() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M || Settings.canDrawOverlays(this@PokemonsDetailActivity)) {
          val intent= Intent(this@PokemonsDetailActivity, OverlayService::class.java)
            intent.putExtra(IntentKey.KEY_STRING_POKEMON_NAME,pokemonName)
            intent.putExtra(IntentKey.KEY_STRING_POKEMON_FRONT_IMAGE,pokemonFrontImg)
            intent.putExtra(IntentKey.KEY_STRING_POKEMON_BACK_IMAGE,pokemonBackImg)
            startService(intent)
        } else {
            Log.e("message error", "service error")
        }
    }
}