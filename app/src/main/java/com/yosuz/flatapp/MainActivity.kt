package com.yosuz.flatapp

//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yosuz.flatapp.components.BottomNavigation
import com.yosuz.flatapp.components.BottomNavigationHorizontal
import com.yosuz.flatapp.components.HomeScreen
import com.yosuz.flatapp.components.UpperBar
import com.yosuz.flatapp.navigation.FlatAppRouter
import com.yosuz.flatapp.navigation.Screen
import com.yosuz.flatapp.ui.theme.FlatAppTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            val orientation = this.resources.configuration.orientation
            var orientationPortrait = true

            if (orientation == Configuration.ORIENTATION_PORTRAIT){
                orientationPortrait = true
            } else {
                orientationPortrait = false
            }

            Crossfade(targetState = FlatAppRouter.currentScreen){ currentState ->
                when(currentState.value){
                    is Screen.LoginScreen -> {
                        if(orientationPortrait){
                            FirstScreen()
                        } else {
                            FirstScreenHorizontal()
                        }
                    }
                    is Screen.HomeScreen -> {
                        if(orientationPortrait){
                            FlatAppPortrait()
                        } else {
                            FlatAppLandscape()
                        }
                    }
                    is Screen.MapScreen -> {
                        if(orientationPortrait){
                            MapScreenWithBar()
                        } else {
                            MapScreenWithBarHorizontal()
                        }
                    }
                    is Screen.AnimationScreen -> {
                        AnimationScreen()
                    }
                }
            }

            //MapScreen()
//            if (orientation == Configuration.ORIENTATION_PORTRAIT){
//                FlatAppPortrait()
//            }
//            else {
//                FlatAppLandscape()
//            }
        }
    }
}



@Composable
fun FlatAppPortrait() {
    FlatAppTheme {
        Scaffold(
            topBar = { UpperBar()},
            bottomBar = { BottomNavigation() }
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
} //d-_-b

@Composable
fun FlatAppLandscape(){
    FlatAppTheme {
        Surface (color = MaterialTheme.colorScheme.background){
            Row(modifier = Modifier.fillMaxSize()){
                BottomNavigationHorizontal()
                HomeScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

//@Composable
//fun MenuScreen() {
//    val registrationUIState = remember {mutableStateOf(RegistrationUIState())}
//
//    FlatAppTheme {
//        Scaffold(
//            topBar = { UpperBar() },
//            bottomBar = { BottomNavigation() }
//
//        ) { padding ->
//            Menu(modifier = Modifier
//                .padding(padding)
//                .fillMaxWidth(),
//                name = registrationUIState.value.name,
//                email = "email")
//        }
//    }
//}
//
//@Preview
//@Composable
//fun MenuScreenPreview(){
//    MenuScreen()
//}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun FlatAppPortraitPreview() {
    FlatAppPortrait()
}

@Preview(widthDp = 640, heightDp = 360)
@Composable
fun FlatAppLandscapePreview() {
    FlatAppLandscape()
}