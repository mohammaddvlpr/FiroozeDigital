package com.firooze.digital.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.firooze.digital.R
import com.firooze.digital.screen.home.models.HomeScreenState
import com.firooze.digital.screen.home.models.NewsUiModel
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onNavigateToDetail: (id: String) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val pagingItems = viewModel.pagingFlow.collectAsLazyPagingItems()

    HomeScreenContent(
        state = state,
        onNavigateToDetail = onNavigateToDetail,
        onSnackBarShowed = viewModel::onSnackBarShowed,
        pagingItems = pagingItems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onNavigateToDetail: (id: String) -> Unit,
    onSnackBarShowed: () -> Unit,
    pagingItems: LazyPagingItems<NewsUiModel>,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val messageString =
        if (state.message != 0)
            stringResource(id = state.message, state.newNewsCount)
        else
            null

    LaunchedEffect(messageString) {
        messageString?.let {
            snackbarHostState.showSnackbar(it)
            onSnackBarShowed()
        }
    }

    Scaffold(modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.latest_news)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    titleContentColor = Color.White
                )
            )
        }) {
        LazyColumn(
            Modifier.padding(it),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                count = pagingItems.itemCount,
                key = pagingItems.itemKey { model -> model.id }
            ) { index ->
                val model = pagingItems[index]
                if (model != null)
                    NewsItem(
                        modifier = Modifier.fillParentMaxWidth(),
                        onClick = { onNavigateToDetail(model.id) },
                        model = model
                    )
            }

            pagingItems.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { Loading(modifier = Modifier.fillParentMaxSize()) }
                    }

                    loadState.refresh is LoadState.Error -> {
                        val error = pagingItems.loadState.refresh as LoadState.Error
                        item {
                            Error(
                                modifier = Modifier.fillParentMaxSize(),
                                message = error.error.localizedMessage
                            ) { retry() }
                        }
                    }

                    loadState.append is LoadState.Loading -> {
                        item { LoadingNextPageItem(modifier = Modifier) }
                    }

                    loadState.append is LoadState.Error -> {
                        val error = pagingItems.loadState.append as LoadState.Error
                        item {
                            Error(
                                modifier = Modifier,
                                message = error.error.localizedMessage
                            ) { retry() }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.loading_message),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CircularProgressIndicator(Modifier.padding(top = 10.dp))
    }
}

@Composable
fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Composable
fun Error(
    modifier: Modifier = Modifier,
    message: String?,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message ?: stringResource(id = R.string.unknown_error_message),
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(id = R.string.retry))
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    val fakeNewsUiModel = NewsUiModel(
        id = "0",
        title = "Sample title",
        imageUrl = "",
        dateAndTime = "2024/10/06 08:10",
        summary = "this is a sample summary"
    )

    HomeScreenContent(
        state = HomeScreenState(),
        onNavigateToDetail = { },
        onSnackBarShowed = {},
        pagingItems = flow { emit(PagingData.from(listOf(fakeNewsUiModel))) }.collectAsLazyPagingItems()
    )
}

@Composable
fun NewsItem(modifier: Modifier = Modifier, onClick: (NewsUiModel) -> Unit, model: NewsUiModel) {
    Card(
        modifier = modifier,
        onClick = { onClick(model) },
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, pressedElevation = 12.dp),
        shape = MaterialTheme.shapes.medium
    ) {

        Row(Modifier.padding(8.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(model.imageUrl)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.img_place_holder),
                error = painterResource(id = R.drawable.img_place_holder),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .size(96.dp),
                contentScale = ContentScale.Crop,

                contentDescription = "News Image"
            )

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = model.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.titleMedium,
                )

                Text(
                    text = model.summary,
                    style = MaterialTheme.typography.labelLarge
                )


                Text(
                    text = model.dateAndTime,
                    style = MaterialTheme.typography.labelMedium
                )


            }

        }
    }
}

@Preview
@Composable
fun NewsItemPreview() {
    Box(
        Modifier
            .background(Color.White)
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        NewsItem(
            modifier = Modifier.fillMaxWidth(),
            onClick = {},
            model = NewsUiModel(
                id = "0",
                title = "Sample title",
                imageUrl = "https://gratisography.com/wp-content/uploads/2024/01/gratisography-cyber-kitty-800x525.jpg",
                dateAndTime = "2024/10/06 08:10",
                summary = "this is a sample summary"
            )
        )
    }
}