package com.eati.pexels.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.eati.pexels.R
import com.eati.pexels.domain.Photo


@Composable
fun PhotosScreen(viewModel: PhotosViewModel) {
    val result by viewModel.photosFlow.collectAsState()

    Photos(result, viewModel::updateResults)

}

@Composable
fun Photos(results: List<Photo>, updateResults: (String) -> Unit) {

    var search by remember {
        mutableStateOf("California")
    }
    val focusManager =  LocalFocusManager.current


    LaunchedEffect(Unit) {
        updateResults(search)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp)
    ) {
        Text(
            text = "EATI Final Project",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = search,
            onValueChange = { search = it },
            placeholder = {
                Text(text = "Busqueda")
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
            singleLine = true,
            keyboardActions = KeyboardActions(onDone = {
                updateResults(search)
                focusManager.clearFocus()
            })
        )


        LazyColumn(){
            items(results){item ->

               PhotoCard(item)

            }
        }

    }
}

@Composable
fun PhotoCard(photo: Photo) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)

    ) {

        AsyncImage(
            model = photo.url,
            contentDescription = photo.alt,
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )

        Text(text = "Descripción: " + photo.alt)
        Hyperlink(url = photo.photographerUrl)

        if (expanded) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "Datos de la imagen:")
                Text(text = "Ancho: " + photo.width + "px" , Modifier.padding(start = 16.dp))
                Text(text = "Alto: " + photo.height + "px", Modifier.padding(start = 16.dp))
                Text(text = "Color promedio: " + photo.avgColor , Modifier.padding(start = 16.dp))
            }
        }
        

    }
}

@Composable
fun Hyperlink(url : String) {

    val annotatedString = buildAnnotatedString {
        append("Ve quién tomó esta foto")

        addStyle(
            style = SpanStyle(
                color = Color.Cyan,
                textDecoration = TextDecoration.Underline
            ),
            start = 0,
            end = length
        )

        addStringAnnotation(
            "URL",
            url,
            start = 0,
            end = length
        )
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations("URL", offset, offset)
                .firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        }
    )
}