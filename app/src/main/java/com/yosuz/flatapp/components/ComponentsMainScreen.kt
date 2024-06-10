package com.yosuz.flatapp.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Diversity3
import androidx.compose.material.icons.filled.DoNotDisturbOn
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.yosuz.flatapp.based.User
import com.yosuz.flatapp.data.SignupViewModel
import com.yosuz.flatapp.data.RegistrationUIState
import com.yosuz.flatapp.navigation.FlatAppRouter
import com.yosuz.flatapp.navigation.Screen
import com.yosuz.flatapp.ui.theme.FlatAppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun UpperBar(modifier: Modifier = Modifier) {
    val showFriends =  remember { mutableStateOf(false) }
    val showMenu =  remember { mutableStateOf(false) }

    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ){
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Diversity3,
                    contentDescription = null,
                    modifier = Modifier.clickable{
                        showFriends.value = !showFriends.value
                    }
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
                    contentDescription = null,
                    modifier = Modifier.clickable{
                        showMenu.value = !showMenu.value
                    }
                )
            },
            selected = false,
            onClick = {}
        )
    }

    if (showFriends.value){
        Dialog(onDismissRequest = { showFriends.value = false}){
            Box {
                FriendList()
            }
        }
    }

    val registrationUIState = remember {mutableStateOf(RegistrationUIState())} //?? TODO

    if (showMenu.value){
        val usr_name = remember { mutableStateOf<String>("Placeholder") }
        val usr_email = remember { mutableStateOf<String>("Placeholder") }
        val db = Firebase.database.reference
        val uid = Firebase.auth.uid
        Log.i("firebase","Uid: ${uid}")
        val myRef = db.child("users").child(uid.toString())
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.getValue(object :
                    GenericTypeIndicator<User>() {})
                if (data != null) {
                    usr_name.value = data.name.toString()
                    usr_email.value = data.email.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Error", "Error $error")
            }


        })

        Dialog(onDismissRequest = { showMenu.value = false}){
            Box {
                Menu(modifier = Modifier,
                    name = usr_name.value, //nie działa TODO
                    email = usr_email.value) //nie działa TODO
            }
        }
    }
}

@Composable
fun BottomNavigation(modifier: Modifier = Modifier) {

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
            selected = false,
            onClick = {FlatAppRouter.navigateTo(Screen.HomeScreen)} //nie jestem jeszcze pewna czy działa, okaże się, jak będą mapy
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
fun BottomNavigationHorizontal(modifier: Modifier = Modifier) {
    val showFriends =  remember { mutableStateOf(false) }

    NavigationRail(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState()),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ){
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            NavigationRailItem(
                icon = {
                    Icon (
                        imageVector = Icons.Default.Home,
                        contentDescription = null
                    )
                },
                label = {
                    Text (
                        text =  "Home"
                    )
                },
                selected = false,
                onClick = {FlatAppRouter.navigateTo(Screen.HomeScreen)}
            )
            Spacer(modifier = Modifier.height(25.dp))
            NavigationRailItem(
                icon = {
                    Icon (
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
            Spacer(modifier = Modifier.height(25.dp))
            NavigationRailItem(
                icon = {
                    Icon (
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
            Spacer(modifier = Modifier.height(25.dp))
            NavigationRailItem(
                icon = {
                    Icon (
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
            Spacer(modifier = Modifier.height(25.dp))
            NavigationRailItem(
                icon = {
                    Icon (
                        imageVector = Icons.Default.Diversity3,
                        contentDescription = null,
                        modifier = Modifier.clickable{
                            showFriends.value = !showFriends.value
                        }
                    )
                },
                label = {
                    Text(
                        text = "Friends"
                    )
                },
                selected = false,
                onClick = {}
            )
            Spacer(modifier = Modifier.height(25.dp))
            NavigationRailItem(
                icon = {
                    Icon (
                        imageVector = Icons.Default.Menu,
                        contentDescription = null
                    )
                },
                label = {
                    Text(
                        text = "Menu"
                    )
                },
                selected = false,
                onClick = {}
            )
        }
    }

    if (showFriends.value){
        Dialog(onDismissRequest = { showFriends.value = false}){
            Box {
                FriendList()
            }
        }
    }
}



@Composable
fun MyChores(modifier: Modifier = Modifier) {
    val expanded = remember { mutableStateOf(false) }
    //val extraPadding = if (expanded.value) 48.dp else 0.dp
    val myChore = remember{ mutableIntStateOf(0) }

    val db = Firebase.database.reference
    val uid = Firebase.auth.uid
    Log.i("firebase","Uid: ${uid}")
    val myRef = db.child("users").child(uid.toString())
    myRef.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val data = dataSnapshot.getValue(object :
                GenericTypeIndicator<User>() {})
            if (data != null) {
                var days = ChronoUnit.DAYS.between(LocalDate.parse("01.06.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")),LocalDate.now())
                myChore.value = ((data.chore + days) % 4).toInt()
                Log.i("firebase","??? ${myChore.value}  ${days.toInt() % 4} ")

            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Error", "Error $error")
        }


    })


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
            when(myChore.value)
            {
                0 -> Kitchen()
                1 -> Bathroom()
                2 -> Trash()
                3 -> Floor()
            }
        }
    }
}
//chores:
//imageVector = Icons.Default.Countertops -> Kitchen
//imageVector = Icons.Default.Bathtub -> Bathroom
//imageVector = Icons.Default.Delete -> Trash
//imageVector = Icons.Default.CleaningServices -> Floor

@Composable
fun TextFieldWithButton(buttonText: String,onButtonClick: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter text") },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = { onButtonClick(text) },
            modifier = Modifier.alignByBaseline()
        ) {
            Text(buttonText)
        }
    }
}

fun addShoppingItem(item: String)
{
    if(item.trim()!="")
    {
        val db = Firebase.database.reference
        val ref = db.child("shopping-list").child(item).setValue(false).addOnCompleteListener{
            if(it.isSuccessful)
            {
                Log.i("firebase","Added item")
            }
            else
            {
                Log.e("firebase","Couldn't add item")
            }
        }

    }



}

@Composable
fun ShoppingList(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }


    //val extraPadding = if (expanded.value) 48.dp else 0.dp


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
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = "Shopping List",
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                modifier = Modifier.size(55.dp),
                imageVector = Icons.Default.Checklist,
                contentDescription = null
            )
        }
        if (expanded) {
            val items = remember { mutableStateOf<Map<String, Boolean>>(emptyMap()) }

            val db = Firebase.database.reference

            val ref = db.child("shopping-list")

            ref.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val data = dataSnapshot.getValue(object :
                        GenericTypeIndicator<Map<String, Boolean>>() {})
                    if (data != null) {
                        items.value = data
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Error", "Error $error")
                }


            })

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 75.dp, bottom = 20.dp)
                    .padding(horizontal = 25.dp)
            ) {
                TextFieldWithButton("Add item",onButtonClick = ::addShoppingItem)
                for ((key, value) in items.value) {
                    //Text(text = "$key: $value", fontSize = 20.sp)
//                    val checkedState = remember { mutableStateOf(false) }
                    var decor = TextDecoration.None
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = value,
                            onCheckedChange = { ref.child(key).setValue(it) })
                        decor = if (value) {
                            TextDecoration.LineThrough
                        } else {
                            TextDecoration.None
                        }
                        Text(
                            key,
                            textDecoration = decor,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun Friend(usr:User,modifier: Modifier = Modifier)
{
    var days = ChronoUnit.DAYS.between(LocalDate.parse("01.06.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")),LocalDate.now())
    var myChore = ((usr.chore + days) % 4).toInt()
    Row(
        modifier = Modifier.padding(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(
            //modifier = Modifier.size(55.dp),
            imageVector = Icons.Default.DoNotDisturbOn,
            contentDescription = null
        )
        Spacer(Modifier.width(10.dp))

        Text(modifier = Modifier
            .weight(1f),
            text = usr.name.toString(),
            style = MaterialTheme.typography.titleLarge)

        Column(modifier = Modifier,
            //.padding(bottom = extraPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            when(myChore)
            {
                0 -> Kitchen()
                1 -> Bathroom()
                2 -> Trash()
                3 -> Floor()
            }
        }
    }
}

@Composable
fun FriendList(modifier: Modifier = Modifier){

    val users = remember { mutableStateOf<Map<String, User>>(emptyMap()) }
//    val users = listOf("Alex", "Kuba", "Kamila")
    val icons = listOf(
        Icons.Default.Countertops, Icons.Default.Bathtub,
        Icons.Default.Delete, Icons.Default.CleaningServices )

    val db = Firebase.database.reference
    val uid = Firebase.auth.uid
    Log.i("firebase","Uid: ${uid}")
    val myRef = db.child("users")
    myRef.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            val data = dataSnapshot.getValue(object :
                GenericTypeIndicator<Map<String, User>>() {})
            if (data != null) {

                users.value = data


            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e("Error", "Error $error")
        }


    })


    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp))
    {
        LazyColumn(){
            for((key, value) in users.value){
                if(key == uid)
                    continue
                item{
                    Friend(value)
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
fun Menu(modifier: Modifier = Modifier, name: String, email: String, signupViewModel: SignupViewModel = viewModel() ){
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
            .padding(vertical = 8.dp, horizontal = 8.dp))
    {
        LazyColumn {
            item {
                Row(
                    modifier = Modifier
                        .padding(24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(40.dp),
                        imageVector = Icons.Default.Person,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(15.dp))
                    Column(
                        modifier = Modifier,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier,
                            text = name,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            modifier = Modifier,
                            text = email,
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                }
                Button(
                    onClick = { signupViewModel.logout() },
                    modifier = Modifier
                        //.fillMaxWidth()
                        .padding(top = 5.dp)
                        .heightIn(40.dp),
                    contentPadding = PaddingValues(),
                    //colors = ButtonDefaults.buttonColors(Color.Transparent),
                    enabled = true
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(30.dp)
                            .background(
                                color = MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(50.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Logout",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                //Spacer(modifier = Modifier.height(8.dp))
//                ButtonComponent(value = "Logout",
//                    onButtonClicked = {loginViewModel.logout()},
//                    isEnabled = true)
//            }

            }
        }
    }
}

@Preview (showBackground = true, widthDp = 320)
@Composable
fun MenuPreview(){
    Menu(modifier = Modifier, "name", "email", viewModel())
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
@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun BottomNavigationHorizontalPreview() {
    FlatAppTheme { BottomNavigationHorizontal() }
}
@Preview
@Composable
fun FriendListPreview(){
    FriendList()
}
@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen()
}