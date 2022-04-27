package com.example.pokemoncase.ui.layout

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

import com.ajguan.library.ILoadMoreView;
import android.view.View
import android.R

import androidx.annotation.NonNull

import android.view.ViewGroup

import android.widget.TextView
import androidx.annotation.Nullable


class CustomLoadMoreView(context: Context,view: View) : FrameLayout(context),ILoadMoreView {


    private var loadingStr = ""
    private var retryStr = ""
    private lateinit var view:View

init {
    init(context)
    this.view=view
}


    private fun init(context: Context) {
        inflate(context, com.example.pokemoncase.R.layout.custom_load_more, this)

    }

    fun setStrings(loadingStr: String, retryStr: String) {
        this.loadingStr = loadingStr
        this.retryStr = retryStr

    }

    override fun reset() {

    }

    override fun loading() {

    }

    override fun loadComplete() {

    }

    override fun loadFail() {

    }

    override fun loadNothing() {}

    override fun getCanClickFailView(): View? {
       return view
    }

}