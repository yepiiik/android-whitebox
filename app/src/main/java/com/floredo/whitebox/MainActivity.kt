package com.floredo.whitebox

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.floredo.whitebox.ui.MainScreen
import com.floredo.whitebox.ui.theme.WhiteboxTheme // Make sure this matches your theme name

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WhiteboxTheme {
                MainScreen()
            }
        }
    }
}