package com.example.infiniterainbow.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.infiniterainbow.R
import com.example.infiniterainbow.presentation.screens.ColorListScreen
import com.example.infiniterainbow.presentation.screens.FavoritesScreen
import com.example.infiniterainbow.presentation.screens.PaletteScreen
import com.example.infiniterainbow.presentation.viewmodel.RainbowViewModel
import com.example.infiniterainbow.util.getHexString
import com.example.infiniterainbow.util.getRgbString

private var colorInfoToast: Toast? = null

@Composable
fun InfiniteRainbowApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val isDark = isSystemInDarkTheme()
    val navBarBackground = if (isDark) ComposeColor.Black else ComposeColor.White
    val contentColor = if (isDark) ComposeColor.White else ComposeColor.Black
    
    // Scoped to the Activity by default when called here
    val rainbowViewModel: RainbowViewModel = viewModel()

    Scaffold(
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route == "color_list" || currentDestination?.route == "favorites") {
                BottomNavigation(
                    backgroundColor = navBarBackground,
                    contentColor = contentColor
                ) {
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = R.drawable.ic_baseline_format_align), contentDescription = null) },
                        label = { Text("Explore") },
                        selected = currentDestination.hierarchy.any { it.route == "color_list" },
                        onClick = {
                            navController.navigate("color_list") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                    BottomNavigationItem(
                        icon = { Icon(painterResource(id = R.drawable.ic_favorites), contentDescription = null) },
                        label = { Text("Favorites") },
                        selected = currentDestination.hierarchy.any { it.route == "favorites" },
                        onClick = {
                            navController.navigate("favorites") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "color_list",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("color_list") {
                ColorListScreen(
                    viewModel = rainbowViewModel,
                    onCardClick = { colorInt ->
                        navController.navigate("palette/$colorInt")
                    }
                )
            }
            composable("favorites") {
                FavoritesScreen()
            }
            composable(
                route = "palette/{colorInt}",
                arguments = listOf(navArgument("colorInt") { type = NavType.IntType })
            ) { backStackEntry ->
                val colorInt = backStackEntry.arguments?.getInt("colorInt") ?: 0
                PaletteScreen(
                    colorInt = colorInt,
                    viewModel = rainbowViewModel,
                    colorName = formatColorName(context, colorInt),
                    onCopyClick = { copyToClipboard(context, it) },
                    onCardClick = { tappedColorFromPalette ->
                        navController.navigate("palette/$tappedColorFromPalette")
                    }
                )
            }
        }
    }
}

private fun copyToClipboard(context: Context, intColor: Int) {
    val hexColor = formatColorName(context, intColor)
    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(context.getString(R.string.color), hexColor)
    clipboard.setPrimaryClip(clip)
    updateToastMsg(context, context.getString(R.string.copied))
}

private fun updateToastMsg(context: Context, msg: String) {
    colorInfoToast?.cancel()
    colorInfoToast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
    colorInfoToast?.show()
}

private fun formatColorName(context: Context, colorInt: Int): String {
    val hexColor = getHexString(colorInt)
    val rgbColor = getRgbString(colorInt)
    return context.getString(R.string.full_color_name, rgbColor, hexColor)
}
