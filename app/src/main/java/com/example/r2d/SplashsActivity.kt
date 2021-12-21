package com.example.r2d

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity


class SplashsActivity : AppCompatActivity() {
    private var logo: ImageView? = null
    protected override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashs)
        logo = findViewById<ImageView>(R.id.logo)
        Handler().postDelayed({
            val i = Intent(this@SplashsActivity, new::class.java)
            startActivity(i)
            finish()
        }, splashTimeOut.toLong())
        val myanim = AnimationUtils.loadAnimation(this, R.anim.mysplashanimation)
        logo!!.startAnimation(myanim)
    }

    companion object {
        private const val splashTimeOut = 2000
    }
}