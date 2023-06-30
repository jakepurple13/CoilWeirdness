package com.programmersbox.coilweirdness

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderDefaults
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.material.shimmerHighlightColor
import com.google.accompanist.placeholder.placeholder
import com.programmersbox.coilweirdness.ui.theme.CoilWeirdnessTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoilWeirdnessTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "first"
                    ) {
                        composable("first") {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Button(onClick = { navController.navigate("second") }) {
                                    Text("Go!")
                                }
                            }
                        }

                        composable("second") {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                LazyColumn(
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(10) {
                                        ImageScreen(onClick = { navController.popBackStack() })
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

val imageUrl = "https://images3.alphacoders.com/128/1287842.jpg"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageScreen(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(1.dp),
        modifier = modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_background),
            onSuccess = {
                println("JJJ Success!")
            },
            onLoading = {
                println("JJJ Loading!")
            },
            onError = {
                println("JJJ Error!")
                it.result.throwable.printStackTrace()
            }
        )
        Box(
            Modifier.placeholder(
                visible = painter.state !is AsyncImagePainter.State.Success,
                color = PlaceholderDefaults.shimmerHighlightColor(),
                highlight = PlaceholderHighlight.shimmer()
            )
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(152.dp)
            )

            Box(
                contentAlignment = Alignment.BottomStart,
                modifier = Modifier.matchParentSize()
            ) {
                ListItem(
                    headlineContent = { Text("Text") },
                    supportingContent = { Text("More Text")},
                    colors = ListItemDefaults.colors(
                        containerColor = Color.Transparent,
                        headlineColor = MaterialTheme.colorScheme.surface,
                        supportingColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = .9f),
                                    MaterialTheme.colorScheme.onSurface,
                                )
                            ),
                        )
                        .padding(top = 8.dp)
                )
            }
        }
    }
}