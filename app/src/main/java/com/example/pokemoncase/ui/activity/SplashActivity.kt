package com.example.pokemoncase.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.pokemoncase.R
import com.example.pokemoncase.databinding.ActivitySplashBinding
import com.example.pokemoncase.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import android.provider.MediaStore

import androidx.annotation.NonNull


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val SPLASH_TIME: Long = 2000
    //lateinit var manager: ConnectivityManager
    var isCheckPermission: Boolean = false

    override fun getLayoutRes(): Int {
        return R.layout.activity_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionCheck()
        intialize()
    }

    fun intialize() {
       // manager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        Handler(Looper.myLooper()!!).postDelayed({
            if (manager.activeNetwork != null && isCheckPermission == true) {
                startActivity(Intent(this, MainActivity::class.java))
                finish()

            } else if (manager.activeNetwork == null) {
                viewBinding.tvInternetConnection.setText(R.string.error_internet)
                viewBinding.btnRetry.visibility=View.VISIBLE
                onclickListeneer()
            } else {
                onclickListeneer()
            }
        }, SPLASH_TIME)
    }

    override fun onRestart() {
        super.onRestart()
        permissionCheck()
        intialize()
    }


    fun onclickListeneer() {
        viewBinding.btnPermission.setOnClickListener { goToSettings() }
        viewBinding.btnRetry.setOnClickListener { onRestart() }
    }


    private fun permissionCheck() {
        if (android.os.Build.VERSION.SDK_INT >= 23 && !Settings.canDrawOverlays(this)) {
            Toast.makeText(this, R.string.ACTION_MANAGE_OVERLAY_NO_PERMISSION, Toast.LENGTH_LONG)
                .show()
            viewBinding.btnPermission.visibility = View.VISIBLE
            isCheckPermission = false
        } else {
            isCheckPermission = true
        }
    }

    fun goToSettings() {
        if (isCheckPermission == false) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_OVERLAY_PERMISSION
            val uri = Uri.fromParts("package", this@SplashActivity.getPackageName(), null)
            intent.data = uri
            this@SplashActivity.startActivity(intent)
        }
    }

}