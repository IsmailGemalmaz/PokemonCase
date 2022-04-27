package com.example.pokemoncase.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import com.example.pokemoncase.R
import android.graphics.PixelFormat
import android.view.*
import android.view.MotionEvent

import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.view.Gravity

import android.util.DisplayMetrics
import android.graphics.Rect
import android.media.Image
import android.view.LayoutInflater
import android.widget.*
import androidx.databinding.DataBindingUtil
import com.example.pokemoncase.constant.IntentKey
import com.example.pokemoncase.databinding.OverlayLayoutBinding
import com.example.pokemoncase.util.downloadFromUrl
import com.example.pokemoncase.util.placeHolderProgresBar


class OverlayService : Service() {

    private var mContext: Context? = null
    private var mWindowManager: WindowManager? = null
    private var mView: View? = null
    private lateinit var pokemonName:String
    private lateinit var pokemonBackImg:String
    private lateinit var pokemonFrontImg:String


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        mContext = this;
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        pokemonName= intent?.getStringExtra(IntentKey.KEY_STRING_POKEMON_NAME).toString()
        pokemonBackImg= intent?.getStringExtra(IntentKey.KEY_STRING_POKEMON_BACK_IMAGE).toString()
        pokemonFrontImg= intent?.getStringExtra(IntentKey.KEY_STRING_POKEMON_FRONT_IMAGE).toString()
        mWindowManager = getSystemService(WINDOW_SERVICE) as WindowManager;
        allAboutLayout(intent!!);
        moveView();
        return super.onStartCommand(intent, flags, startId)

    }

    override fun onDestroy() {
        if(mView !=null){
            mWindowManager?.removeView(mView)
        }
        super.onDestroy()
    }

    var mWindowsParams: WindowManager.LayoutParams? = null
    private fun moveView() {

        val metrics = mContext!!.resources.displayMetrics
        val width = (metrics.widthPixels * 0.5f).toInt()
        val height = (metrics.heightPixels * 0.30f).toInt()

        mWindowsParams = WindowManager.LayoutParams(
            width,  //WindowManager.LayoutParams.WRAP_CONTENT,
            height,  //WindowManager.LayoutParams.WRAP_CONTENT,
            //WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
            if (Build.VERSION.SDK_INT <= 25) WindowManager.LayoutParams.TYPE_PHONE else WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,  //WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
            WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,  // Not displaying keyboard on bg activity's EditText
            //WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, //Not work with EditText on keyboard
            PixelFormat.TRANSLUCENT
        )


        mWindowsParams!!.gravity = Gravity.TOP or Gravity.LEFT
        //params.x = 0;
        //params.x = 0;
        mWindowsParams!!.y = 50
        mWindowManager!!.addView(mView, mWindowsParams)

        mView!!.setOnTouchListener(object : OnTouchListener {
            private var initialX = 20
            private var initialY = 20
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            var startTime = System.currentTimeMillis()
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (System.currentTimeMillis() - startTime <= 300) {
                    return false
                }
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = mWindowsParams!!.x
                        initialY = mWindowsParams!!.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                    }
                    MotionEvent.ACTION_UP -> {
                    }
                    MotionEvent.ACTION_MOVE -> {
                        mWindowsParams!!.x = initialX + (event.rawX - initialTouchX).toInt()
                        mWindowsParams!!.y = initialY + (event.rawY - initialTouchY).toInt()
                        mWindowManager!!.updateViewLayout(mView, mWindowsParams)
                    }
                }
                return false
            }
        })
    }


    fun allAboutLayout(intent:Intent){
        val layoutInflater = mContext!!.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        mView = layoutInflater.inflate(R.layout.overlay_layout, null)
        val pokemonName:TextView= mView!!.findViewById<View>(R.id.pokemonName) as TextView
        val btnClose:Button=mView!!.findViewById<View>(R.id.btnServiceClose) as Button
        val imgBack:ImageView=mView!!.findViewById<View>(R.id.ivPokemonBack) as ImageView
        val imgFront:ImageView=mView!!.findViewById<View>(R.id.ivPokemonFront) as ImageView

        pokemonName.text=this.pokemonName
        imgBack.downloadFromUrl(this.pokemonBackImg, placeHolderProgresBar(mContext!!))
        imgFront.downloadFromUrl(this.pokemonFrontImg, placeHolderProgresBar(mContext!!))
        btnClose.setOnClickListener { stopSelf() }
    }

}