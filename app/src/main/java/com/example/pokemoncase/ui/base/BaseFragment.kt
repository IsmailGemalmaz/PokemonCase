package com.example.pokemoncase.ui.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.example.pokemoncase.ui.activity.MainActivity

abstract class BaseFragment<DB : ViewDataBinding> : Fragment()  {

    var mainAct: MainActivity? = null

    protected lateinit var viewBinding: DB


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainAct = context as MainActivity
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return viewBinding.root
    }



}