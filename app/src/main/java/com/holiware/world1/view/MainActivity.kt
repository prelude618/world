package com.holiware.world1.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.holiware.world1.view.theme.World1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            World1Theme {
                val navHostController = rememberNavController()
                AppNavigation(navHostController = navHostController)
            }
        }
    }
}
