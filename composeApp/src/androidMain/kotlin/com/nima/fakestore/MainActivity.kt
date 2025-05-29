package com.nima.fakestore

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.nima.fakestore.ui.theme.AppTheme
import com.nima.fakestore.navigation.AppNavigation

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme (
                isSystemInDarkTheme()
            ) {
                val view = LocalView.current
                val window = (view.context as Activity).window

                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isSystemInDarkTheme()
                Scaffold {it ->
                    Surface(
                        modifier = Modifier.padding(it)
                    ) {
                        AppNavigation()
                    }
                }
            }
        }
    }
}
