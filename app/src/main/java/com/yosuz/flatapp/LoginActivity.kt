package com.yosuz.flatapp

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Surface
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import com.yosuz.flatapp.ui.theme.FlatAppTheme
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Button
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yosuz.flatapp.data.LoginViewModel
import com.yosuz.flatapp.data.UIEvent

class LoginActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val orientation = this.resources.configuration.orientation
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                FirstScreen()
            } else {
                FirstScreenHorizontal()
            }
        }
    }
}

@Composable
fun ButtonComponent(value: String, onButtonClicked: () -> Unit){
    Button(
        onClick = {onButtonClicked.invoke()},
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp)
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent)
        //enabled = isEnabled
    ){
        Box(modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(50.dp)
            ),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = value,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

        }
    }
}
@Composable
fun TextField(labelValue: String,
              imageVector: ImageVector,
              onTextSelected: (String) -> Unit){
    val textValue = remember { mutableStateOf("") }
    val localFocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = MaterialTheme.colorScheme.primary,
//            focusedLabelColor = MaterialTheme.colorScheme.primary,
//            cursorColor = MaterialTheme.colorScheme.primary
//        ),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        keyboardActions = KeyboardActions{localFocusManager.clearFocus()},
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        })
}
@Composable
fun PasswordField(labelValue: String,
                  imageVector: ImageVector,
                  onTextSelected: (String) -> Unit){
    val passwordValue = remember { mutableStateOf("") }
    val passwordVisible = remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        label = {Text(text = labelValue)},
//        colors = OutlinedTextFieldDefaults.colors(
//            focusedBorderColor = MaterialTheme.colorScheme.primary,
//            focusedLabelColor = MaterialTheme.colorScheme.primary,
//            cursorColor = MaterialTheme.colorScheme.primary
//        ),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done),
        singleLine = true,
        maxLines = 1,
        value = passwordValue.value,
        onValueChange = {
            passwordValue.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = null
            )
        },
        trailingIcon = {
            val iconImage = if(passwordVisible.value){
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            val description = if(passwordVisible.value){
                "Hide password"
            } else {
                "Show password"
            }

            IconButton(onClick = {passwordVisible.value = !passwordVisible.value}){
                Icon(imageVector = iconImage, contentDescription = description)
            }
        },
        visualTransformation = if(passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation()
    )
}
@Composable
fun ClickableTextComponent(initialText: String, clickableText: String, navController: NavController, screen: String){
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.surfaceVariant)){
            pushStringAnnotation(tag = clickableText, annotation = clickableText)
            append(clickableText)
        }
    }
    ClickableText(text = annotatedString,
        onClick = { navController.navigate(screen)})

}
@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Column (modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            )
        {
            Text(modifier = Modifier.padding(top = 50.dp),
                text = "Welcome back,",
                style = MaterialTheme.typography.titleMedium
                )
            Text(modifier = Modifier.padding(vertical = 10.dp),
                text = "Login to FlatApp",
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(labelValue = "Email",
                imageVector = Icons.Default.Email,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.EmailChanged(it))
                })
            Spacer(modifier = Modifier.height(5.dp))
            PasswordField(labelValue = "Password",
                imageVector = Icons.Default.Lock,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                })
            ButtonComponent(value = "Login", onButtonClicked = {loginViewModel.onEvent(UIEvent.RegisterButtonClicked)})
            Spacer(modifier = Modifier.height(10.dp))
            ClickableTextComponent("Don't have an account yet? ", "Register", navController, "registration_screen")
        }
    }
}
@Composable
fun RegistrationScreen(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Column (modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Text(modifier = Modifier.padding(top = 50.dp),
                text = "Welcome,",
                style = MaterialTheme.typography.titleMedium
            )
            Text(modifier = Modifier.padding(vertical = 10.dp),
                text = "Create an Account",
                style = MaterialTheme.typography.headlineMedium
            )
            TextField(labelValue = "Name",
                imageVector = Icons.Default.Person,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.NameChanged(it))
                })
            Spacer(modifier = Modifier.height(5.dp))
            TextField(labelValue = "Email",
                imageVector = Icons.Default.Email,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.EmailChanged(it))
                })
            Spacer(modifier = Modifier.height(5.dp))
            PasswordField(labelValue = "Password",
                imageVector = Icons.Default.Lock,
                onTextSelected = {
                    loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                })
            ButtonComponent(value = "Create", onButtonClicked = {loginViewModel.onEvent(UIEvent.RegisterButtonClicked)})
            Spacer(modifier = Modifier.height(10.dp))
            ClickableTextComponent("Already have an account? ", "Login", navController, "login_screen")
        }
    }
}

@Composable
fun LoginScreenHorizontal(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        LazyColumn (modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            item {
                Text(
                    modifier = Modifier,
                    text = "Welcome back,",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    modifier = Modifier.padding(vertical = 10.dp),
                    text = "Login to FlatApp",
                    style = MaterialTheme.typography.headlineMedium
                )
                TextField(labelValue = "Email",
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.EmailChanged(it))
                    })
                Spacer(modifier = Modifier.height(5.dp))
                PasswordField(labelValue = "Password",
                    imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                    })
                ButtonComponent(value = "Login", onButtonClicked = {loginViewModel.onEvent(UIEvent.RegisterButtonClicked)})
                Spacer(modifier = Modifier.height(10.dp))
                ClickableTextComponent("Don't have an account yet? ", "Register", navController,"registration_screen_horizontal")
            }
        }
    }
}
@Composable
fun RegistrationScreenHorizontal(navController: NavController, loginViewModel: LoginViewModel = viewModel()){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        LazyColumn (modifier = Modifier.padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            item{
                Text(modifier = Modifier,
                    text = "Welcome,",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(modifier = Modifier.padding(vertical = 10.dp),
                    text = "Create an Account",
                    style = MaterialTheme.typography.headlineMedium
                )
                TextField(labelValue = "Name",
                    imageVector = Icons.Default.Person,
                    onTextSelected = {loginViewModel.onEvent(UIEvent.NameChanged(it))})
                Spacer(modifier = Modifier.height(5.dp))
                TextField(labelValue = "Email",
                    imageVector = Icons.Default.Email,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.EmailChanged(it))
                    })
                Spacer(modifier = Modifier.height(5.dp))
                PasswordField(labelValue = "Password",
                    imageVector = Icons.Default.Lock,
                    onTextSelected = {
                        loginViewModel.onEvent(UIEvent.PasswordChanged(it))
                    })
                ButtonComponent(value = "Create", onButtonClicked = {loginViewModel.onEvent(UIEvent.RegisterButtonClicked)})
                Spacer(modifier = Modifier.height(10.dp))
                ClickableTextComponent("Already have an account? ", "Login", navController, "login_screen_horizontal")
            }
        }
    }
}

@Composable
fun FirstScreen(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen"){
        composable("login_screen"){
            LoginScreen(navController)
        }
        composable("registration_screen"){
            RegistrationScreen(navController)
        }
    }
}
@Composable
fun FirstScreenHorizontal(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login_screen_horizontal"){
        composable("login_screen_horizontal"){
            LoginScreenHorizontal(navController)
        }
        composable("registration_screen_horizontal"){
            RegistrationScreenHorizontal(navController)
        }
    }
}

//@Preview
//@Composable
//fun LoginScreenPreview(){
//    LoginScreen()
//}
//@Preview
//@Composable
//fun RegistrationScreenPreview(){
//    RegistrationScreen()
//}
//@Preview(widthDp = 640, heightDp = 360)
//@Composable
//fun LoginScreenHorizontalPreview(){
//    LoginScreenHorizontal()
//}
//@Preview(widthDp = 640, heightDp = 360)
//@Composable
//fun RegistrationScreenHorizontalPreview(){
//    RegistrationScreenHorizontal()
//}
@Preview
@Composable
fun FirstScreenPreview(){
    FirstScreen()
}
@Preview(widthDp = 640, heightDp = 360)
@Composable
fun FirstScreenHorizontalPreview(){
    FirstScreenHorizontal()
}
