package com.actrabajoequipo.recipesapp.ui.splash

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.actrabajoequipo.recipesapp.MainActivity
import com.actrabajoequipo.recipesapp.R
import com.actrabajoequipo.recipesapp.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationRepeat(animation: Animator?) {

            }
        })
        binding.lifecycleOwner = this
    }
}