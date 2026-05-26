package com.example.infiniterainbow

import android.content.ClipData
import android.content.ClipboardManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.infiniterainbow.presentation.screens.ColorListScreen
import com.example.infiniterainbow.presentation.screens.PaletteScreen

class MainActivity : ComponentActivity() {

    private var colorInfoToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "color_list") {
                composable("color_list") {
                    ColorListScreen(
                        onCardClick = { colorInt ->
                            navController.navigate("palette/$colorInt")
                        },
                        onCopyClick = { colorInt ->
                            copyToClipboard(colorInt)
                        }
                    )
                }
                composable(
                    route = "palette/{colorInt}",
                    arguments = listOf(navArgument("colorInt") { type = NavType.IntType })
                ) { backStackEntry ->
                    val colorInt = backStackEntry.arguments?.getInt("colorInt") ?: 0
                    PaletteScreen(
                        colorInt = colorInt,
                        colorName = formatColorName(colorInt),
                        onCopyClick = { copyToClipboard(it) },
                        onCardClick = { tappedColorFromPalette ->
                            navController.navigate("palette/$tappedColorFromPalette")
                        }
                    )
                }
            }
        }
    }

    private fun copyToClipboard(intColor: Int) {
        val hexColor = formatColorName(intColor)
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(getString(R.string.color), hexColor)
        clipboard.setPrimaryClip(clip)
        updateToastMsg(getString(R.string.copied))
    }

    private fun getRgbFromInt(initColor: Int): String {
        return "(" +
                "${Color.red(initColor)}," +
                "${Color.green(initColor)}," +
                "${Color.blue(initColor)}" +
                ")"
    }

    private fun updateToastMsg(msg: String) {
        if (colorInfoToast != null) colorInfoToast?.cancel()
        colorInfoToast = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        colorInfoToast?.show()
    }

    private fun formatColorName(colorInt: Int): String {
        val hexColor = String.format("#%06X", 0xFFFFFF and colorInt)
        val rgbColor = getRgbFromInt(colorInt)
        return getString(R.string.full_color_name, rgbColor, hexColor)
    }
}