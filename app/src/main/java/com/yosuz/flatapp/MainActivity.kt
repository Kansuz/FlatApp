package com.yosuz.flatapp

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
//import androidx.compose.foundation.layout.FlowRowScopeInstance.weight
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.EventBusy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yosuz.flatapp.ui.theme.FlatAppTheme
import com.yosuz.flatapp.ui.theme.NicePink


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlatAppTheme {
                FlatApp(modifier = Modifier.fillMaxSize())
                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }
}

@Composable
private fun UpperBar(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Diversity3,
                    contentDescription = null
                )
            },
            selected = false,
            onClick = {}
        )
        Spacer(modifier = Modifier.width(150.dp))
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null
                )
            },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(
        modifier = modifier,
        containerColor =  MaterialTheme.colorScheme.surfaceVariant
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Home"
                )
            },
            selected = true,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.MyLocation,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "My location"
                )
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Invite"
                )
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DoNotDisturbOn,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = "Busy"
                )
            },
            selected = false,
            onClick = {}
        )
    }
}

@Composable
fun FlatApp(
    modifier: Modifier = Modifier) {
    FlatAppPortrait()
}
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        Column(modifier = Modifier.padding(vertical = 4.dp)){
//            for (name in names){
//                Greeting(name=name)
//            }
//        }
//    }
//} d-_-b

@Composable
fun MyChores(modifier: Modifier = Modifier) {
    val expanded = remember {mutableStateOf(false)}
    //val extraPadding = if (expanded.value) 48.dp else 0.dp
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.padding(vertical = 8.dp, horizontal = 8.dp))
    {
        Row(modifier = Modifier
            .padding(24.dp)
            .clickable { expanded.value = !expanded.value },
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(modifier = Modifier.weight(1f),
                text = "Chores",
                style = MaterialTheme.typography.titleLarge)
            Column(modifier = Modifier,
                //.padding(bottom = extraPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Icon(
                    modifier = Modifier.size(40.dp),
                    imageVector = Icons.Default.Countertops,
                    contentDescription = null
                )
                Text(
                    text = "Kitchen",
                    style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Composable
fun ShoppingList(modifier: Modifier = Modifier) {
    var expanded by remember {mutableStateOf(false)}
    //val extraPadding = if (expanded.value) 48.dp else 0.dp
    val users = listOf("Kasia", "Kamil", "Kuba")
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp)
            .clickable { expanded = !expanded })
    {
        Row(
            modifier = Modifier.padding(24.dp),
            verticalAlignment = Alignment.CenterVertically
            ){
            Text(modifier = Modifier
                .weight(1f),
                text = "Shopping List",
                style = MaterialTheme.typography.titleLarge)
            Icon(
                modifier = Modifier.size(55.dp),
                imageVector = Icons.Default.Checklist,
                contentDescription = null
            )
        }
        if(expanded){
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 75.dp, bottom = 20.dp)
                .padding(horizontal = 25.dp)){
                    for(element in users) {
                        Text(element, textAlign = TextAlign.Center, fontSize = 20.sp)
                    }
                }
            }
        }
    }

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier){
        item{
            Spacer(Modifier.height(16.dp))
            MyChores()
        }
        item{
            ShoppingList()
            Spacer(Modifier.height(16.dp))
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
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun MyChoresPreview() {
    FlatAppTheme {MyChores()}
}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun ShoppingListPreview() {
    FlatAppTheme {ShoppingList()}
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun UpperBarPreview(){
    FlatAppTheme {UpperBar(Modifier.padding(bottom = 24.dp)) }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationPreview() {
    FlatAppTheme {BottomNavigation(Modifier.padding(top = 24.dp)) }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun FlatAppPortraitPreview() {
    FlatAppPortrait()
}