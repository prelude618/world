package com.holiware.world1.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.holiware.world1.model.Coin
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    coin: Coin,
    onNavigateBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(coin.name) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AsyncImage(
                model = coin.image,
                contentDescription = "Coin image",
                modifier = Modifier.size(120.dp),
                contentScale = ContentScale.Fit,
            )

            Text(
                text = coin.symbol.uppercase(),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Text(
                text = "$${String.format(Locale.getDefault(), "%.2f", coin.price)}",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold
            )

            val formattedPriceChange = String.format(Locale.getDefault(), "%.2f", coin.priceChange)
            val isZero = formattedPriceChange == "0.00" || formattedPriceChange == "-0.00"
            val color = when {
                isZero -> Color.Black
                coin.priceChange > 0 -> Color.Green
                else -> Color.Red
            }
            val sign = if (coin.priceChange > 0 && !isZero) "+" else ""
            val displayPriceChange = if (isZero) "0.00" else "$sign$formattedPriceChange"

            Text(
                text = displayPriceChange,
                style = MaterialTheme.typography.titleLarge,
                color = color,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
