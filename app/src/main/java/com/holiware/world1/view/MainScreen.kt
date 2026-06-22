package com.holiware.world1.view

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import kotlinx.coroutines.flow.distinctUntilChanged
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import java.util.Locale
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.holiware.world1.R
import com.holiware.world1.domain.model.Coin
import com.holiware.world1.viewmodel.CoinViewModel
import com.holiware.world1.viewmodel.RequestState
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.res.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: CoinViewModel = koinViewModel(),
    onNavigateToDetail: (Coin) -> Unit,
) {
    val coinStateList by viewModel.coinListState.collectAsStateWithLifecycle()
    var requestState by remember { mutableStateOf<RequestState>(RequestState.Idle) }
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(listState) {
        snapshotFlow {
            !listState.canScrollForward && listState.isScrollInProgress
        }
        .distinctUntilChanged()
        .collect { shouldLoad ->
            if (shouldLoad && coinStateList.isNotEmpty()) {
                viewModel.loadNextPage()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.requestState.collect { state ->
            requestState = state
            if (state is RequestState.Failure) {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetRequestState()
            } else if (state is RequestState.Success) {
                viewModel.resetRequestState()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.app_name)) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            items(coinStateList, key = { it.id }) { coin ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(10.dp)
                        .clickable {
                            onNavigateToDetail(coin)
                        }
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = coin.image,
                            contentDescription = "Coin image",
                            modifier = Modifier
                                .width(50.dp)
                                .height(50.dp),
                            contentScale = ContentScale.FillWidth,
                        )

                        Text(
                            text = coin.symbol.uppercase(),
                            modifier = Modifier
                                .padding(horizontal = 16.dp),
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "$${String.format(Locale.getDefault(), "%.2f", coin.price)}",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (requestState is RequestState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(32.dp))
                    }
                }
            }
        }
    }
}
