package com.jobsity.android.challenge

import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.jobsity.android.challenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val navController: NavController
        get() = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setupWithNavController(navController)
        setSupportActionBar(binding.toolbar)

        binding.bottomNavView.setupWithNavController(navController)
    }

    fun showFab(@DrawableRes iconRes: Int? = null) {
        binding.fab.apply {
            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.scale_up))
            isVisible = true
        }
        changeFabIcon(iconRes)
    }

    fun changeFabIcon(@DrawableRes iconRes: Int? = null) {
        iconRes?.let(binding.fab::setImageResource)
    }

    fun hideFab() {
        binding.fab.apply {
            startAnimation(AnimationUtils.loadAnimation(this@MainActivity, R.anim.scale_down))
            isVisible = false
        }
    }

    fun setFabListener(listener: () -> Unit) {
        binding.fab.setOnClickListener { listener() }
    }

}