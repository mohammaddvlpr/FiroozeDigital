package com.firooze.digital.screen.newsDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.firooze.digital.R
import com.firooze.digital.screen.newsDetail.models.NewsDetailScreenState
import com.firooze.digital.screen.newsDetail.models.NewsDetailUiModel

@Composable
fun NewsDetailScreen(
    viewModel: NewsDetailScreenViewModel = hiltViewModel(),
    onNavigateToParent: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    NewsDetailScreenContent(state = state, onNavigateToParent = onNavigateToParent)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreenContent(
    modifier: Modifier = Modifier,
    state: NewsDetailScreenState,
    onNavigateToParent: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.news_detail)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                ),
                navigationIcon = {
                    Icon(
                        modifier = Modifier.clickable(onClick = onNavigateToParent),
                        imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                        contentDescription = "Navigation icon",
                    )
                }
            )
        },
    ) {
        Column(
            Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {

            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.newsDetailUiModel.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.img_place_holder),
                error = painterResource(id = R.drawable.img_place_holder),
                contentScale = ContentScale.Crop,
                contentDescription = "Content Image"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.newsDetailUiModel.title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.newsDetailUiModel.dateAndTime,
                style = MaterialTheme.typography.labelLarge
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = state.newsDetailUiModel.content,
                style = MaterialTheme.typography.bodyLarge
            )

        }
    }
}

@Preview
@Composable
fun NewsDetailContentPreview() {
    NewsDetailScreenContent(
        state = NewsDetailScreenState(newsDetailUiModel = fakeNewsDetailUiModel),
        onNavigateToParent = {}
    )
}

val fakeNewsDetailUiModel = NewsDetailUiModel(
    id = "0",
    title = "There are five distinct icon themes",
    imageUrl = "https://gratisography.com/wp-content/uploads/2024/01/gratisography-cyber-kitty-800x525.jpg",
    dateAndTime = "2024/10/12 12:45 PM",
    content = "There are five distinct icon themes: Filled, Outlined, Rounded, TwoTone," +
            " and Sharp. Each theme contains the same icons, but with a distinct visual" +
            " style. You should typically choose one theme and use it across your " +
            "application for consistency. For example, you may want to use a property " +
            "or a typealias to refer to a specific theme, so it can be accessed in " +
            "a semantically meaningful way from inside other composables."
)
