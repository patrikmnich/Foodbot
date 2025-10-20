package com.example.foodbot

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.foodbot.viewmodels.AppViewModel
import com.example.foodbot.ui.theme.FoodbotTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

val LocalAppLanguage = staticCompositionLocalOf { "en" }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val model: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val splashscreen = installSplashScreen()
        var keepSplashScreen = true

        splashscreen.setKeepOnScreenCondition { keepSplashScreen }
        lifecycleScope.launch {
            model.fetchFoodplan()
            keepSplashScreen = false
        }

        setContent {
            var language by rememberSaveable { mutableStateOf("en") }

            CompositionLocalProvider(LocalAppLanguage provides language) {
                FoodbotTheme {
                    FoodbotApp(
                        currentLanguage = language,
                        onToggleLanguage = {
                            language = it
                        }
                    )
                }
            }
        }
    }
}

fun Context.getLocalizedContext(language: String): Context {
    val updatedContext = LocaleManager.setLocale(this, language)
    return updatedContext
}


object LocaleManager {
    fun setLocale(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }
}