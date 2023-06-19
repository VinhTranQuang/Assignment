package com.base.ui.component.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.base.databinding.SplashActivityBinding
import com.base.ui.base.BaseActivity
import com.base.SPLASH_DELAY
import com.base.ui.component.breeds.BreedListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity(){

    private lateinit var binding: SplashActivityBinding

    override fun initViewBinding() {
        binding = SplashActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToMainScreen()
    }

    override fun observeViewModel() {
    }

    private fun navigateToMainScreen() {
        Handler().postDelayed({
            val nextScreenIntent = Intent(this, BreedListActivity::class.java)
            startActivity(nextScreenIntent)
            finish()
        }, SPLASH_DELAY.toLong())
    }
}
