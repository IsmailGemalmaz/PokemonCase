package com.example.pokemoncase.ui.base

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<DB:ViewDataBinding> : AppCompatActivity() {
    protected lateinit var viewBinding: DB
    lateinit var manager: ConnectivityManager
    @LayoutRes
    abstract fun getLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, getLayoutRes())
        manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    }
}