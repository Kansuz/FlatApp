package com.yosuz.flatapp

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextMotion
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yosuz.flatapp.navigation.FlatAppRouter
import com.yosuz.flatapp.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun AnimationScreen(){
    var start_animation = true
    val infiniteTransition = rememberInfiniteTransition(label = "infinite transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(tween(1000)),
        label = "scale"
    )
    if(start_animation){
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp))
        {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(

                    text = "FlatApp",
                    color = Color(0xFFE5D9DD),
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            transformOrigin = TransformOrigin.Center
                        }
                        .align(Alignment.Center),
                    style = LocalTextStyle.current.copy(textMotion = TextMotion.Animated)
                )
            }
        }
    }

    LaunchedEffect(key1 = true) {
        start_animation = true
        delay(1000)
        FlatAppRouter.navigateTo(Screen.HomeScreen)
    }
}

@Preview
@Composable
fun AnimationScreenPreview(){
    AnimationScreen()
}