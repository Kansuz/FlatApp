package com.yosuz.flatapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class Screen{
    object LoginScreen : Screen()
    object HomeScreen : Screen()
    object MapScreen : Screen()
    object AnimationScreen: Screen()
}

object FlatAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}