package com.example.pokemoncase.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pokemoncase.databinding.FragmentPokemonsBinding
import com.example.pokemoncase.ui.adapter.PokemonAdapter
import com.example.pokemoncase.ui.viewmodel.PokemonsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.lifecycle.Observer
import com.example.pokemoncase.R
import com.example.pokemoncase.ui.base.BaseFragment
import com.ajguan.library.EasyRefreshLayout.EasyEvent
import com.example.pokemoncase.ui.layout.CustomLoadMoreView
import java.util.*


@AndroidEntryPoint
class PokemonsFragment : BaseFragment<FragmentPokemonsBinding>(),
    PokemonAdapter.OnItemClickListener {

    private val viewModel: PokemonsViewModel by viewModels()

    @Inject
    lateinit var pokemonAdapter: PokemonAdapter

    override fun getLayoutRes(): Int {
        return R.layout.fragment_pokemons
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        viewBinding.lifecycleOwner = viewLifecycleOwner
        observeLiveData()
        initListener()
            viewModel.getPokemon()

    }

    private fun setAdapter() {
        viewBinding.rvPokemon.apply {
            layoutManager = LinearLayoutManager(this@PokemonsFragment.context)
            setHasFixedSize(true)
            adapter = PokemonAdapter()
        }
    }

    private fun observeLiveData() {
        viewModel.pokemon.observe(viewLifecycleOwner, { pokemonList ->
            pokemonList?.let {
                viewBinding.rvPokemon.apply {
                    with(adapter as PokemonAdapter) {
                        pokemons.addAll(it)
                        context = getContext()
                        notifyDataSetChanged()
                    }
                }
            }
        })
        viewModel.pokemonsLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    viewBinding.pokemonsLoading.visibility = View.VISIBLE
                } else {
                    viewBinding.pokemonsLoading.visibility = View.GONE
                }
            }
        })
    }


    fun initListener() {
        var loadmoreView= view?.let { CustomLoadMoreView(requireContext(), it) }
            viewBinding.easyLayout.loadMoreView=loadmoreView
        pokemonAdapter.pokemons.clear()
        viewBinding.easyLayout!!.addEasyEvent(object : EasyEvent {
            override fun onLoadMore() {
                Handler().postDelayed(Runnable {
                        viewBinding.easyLayout.refreshComplete()
                        viewBinding.easyLayout.closeLoadView()
                        viewModel.getPokemon()
                }, 500)
            }

            override fun onRefreshing() {
                Handler().postDelayed(Runnable {
                    viewBinding.easyLayout.refreshComplete()
                        viewBinding.easyLayout.closeLoadView()
                        viewModel.getPokemon()

                }, 1000)
            }
        })

    }

    override fun onClick(position: Int) {

    }


}