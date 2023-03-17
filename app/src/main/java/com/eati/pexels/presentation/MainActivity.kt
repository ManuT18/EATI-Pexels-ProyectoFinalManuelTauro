package com.eati.pexels.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.eati.pexels.presentation.ui.theme.EATIPexelsTheme

class MainActivity : ComponentActivity() {

    private val viewModel: PhotosViewModel by viewModels { ViewModelFactory.factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EATIPexelsTheme {
                Scaffold(
                    topBar = { TopAppBar(title = { Text("Imagenes") }) }) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        PhotosScreen(viewModel)
                    }
                }

            }
        }
    }

}


