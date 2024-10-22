package com.example.ClickerClone

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.clickerclone.R
import com.example.clickerclone.ui.theme.ClickerCloneTheme
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val savedClicks = loadClicks(this)
        setContent {
            ClickerApp(savedClicks)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClickerApp(savedClicks: Int) {

    var currentStep by remember { mutableStateOf(1) }
    val context = LocalContext.current
    var clickCount by remember { mutableStateOf( savedClicks) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.click_display, clickCount),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = Color.Black
                )
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.tertiaryContainer),
            color = Color.Blue
        ) {
            when (currentStep) {
                1 -> {
                    Clicker(
                        drawableResourceId = R.drawable._131_front,
                        contentDescriptionResourceId = R.string.clicker_info_1,
                        onValueChange = { clickCount = clickCount},
                        onImageClick = {
                            clickCount ++
                            saveClicks(context, clickCount)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Clicker(
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onValueChange: (String) -> Unit,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ) {
                Image(
                    painter = painterResource(drawableResourceId),
                    contentDescription = stringResource(contentDescriptionResourceId),
                    modifier = Modifier
                        .width(256.dp)
                        .height(320.dp)
                        .padding(48.dp)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

private fun saveClicks(context: Context, totalClicks: Int) {
    val sharedPreferences = context.getSharedPreferences("LemonadePreferences", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putInt("total_clicks", totalClicks)
    editor.apply()
}
private fun loadClicks(context: Context): Int {
    val sharedPreferences = context.getSharedPreferences("LemonadePreferences", Context.MODE_PRIVATE)
    return sharedPreferences.getInt("total_clicks", 0)
}

@Composable
fun Upgrades(
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(
                onClick = onImageClick,
                shape = RoundedCornerShape(40.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)
            ){

            }
        }
    }
}

@Preview
@Composable
fun ClickerPreview() {
    ClickerCloneTheme() {
        ClickerApp(savedClicks = 0)
    }
}