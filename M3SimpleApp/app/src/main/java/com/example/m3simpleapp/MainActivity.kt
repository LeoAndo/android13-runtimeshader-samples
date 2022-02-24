package com.example.m3simpleapp

import android.os.Bundle
import android.os.LocaleList
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.m3simpleapp.core.ApplicationLocalesService
import com.example.m3simpleapp.viewmodels.ActivityViewModel
import com.example.m3simpleapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
internal class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var applicationLocalesService: ApplicationLocalesService
    private val viewModel by viewModels<ActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAnchorView(R.id.fab)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val localeList = when (item.itemId) {
            R.id.language_settings_system -> applicationLocalesService.applicationLocales =
                LocaleList.getEmptyLocaleList()
            R.id.language_settings_ja -> applicationLocalesService.applicationLocales =
                LocaleList.forLanguageTags("ja")
            R.id.language_settings_ko -> applicationLocalesService.applicationLocales =
                LocaleList.forLanguageTags("ko")
            R.id.language_settings_zh -> applicationLocalesService.applicationLocales =
                LocaleList.forLanguageTags("zh")
            else -> null
        }
        return if (localeList != null) true else super.onOptionsItemSelected(item)
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}