package com.yosuz.flatapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Countertops
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Kitchen(modifier: Modifier = Modifier)
{
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

@Composable
fun Bathroom(modifier: Modifier = Modifier)
{
    Column(modifier = Modifier,
        //.padding(bottom = extraPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.Bathtub,
            contentDescription = null
        )
        Text(
            text = "Bathroom",
            style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun Trash(modifier: Modifier = Modifier)
{
    Column(modifier = Modifier,
        //.padding(bottom = extraPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.Delete,
            contentDescription = null
        )
        Text(
            text = "Trash",
            style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
fun Floor(modifier: Modifier = Modifier)
{
    Column(modifier = Modifier,
        //.padding(bottom = extraPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Icon(
            modifier = Modifier.size(40.dp),
            imageVector = Icons.Default.CleaningServices,
            contentDescription = null
        )
        Text(
            text = "Floor",
            style = MaterialTheme.typography.titleMedium)
    }
}