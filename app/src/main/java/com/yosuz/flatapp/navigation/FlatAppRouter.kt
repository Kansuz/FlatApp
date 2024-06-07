package com.yosuz.flatapp.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.ActivityNavigator

sealed class Screen{
    object LoginScreen : Screen()
    object HomeScreen : Screen()
}

object FlatAppRouter {
    var currentScreen: MutableState<Screen> = mutableStateOf(Screen.LoginScreen)

    fun navigateTo(destination: Screen){
        currentScreen.value = destination
    }
}