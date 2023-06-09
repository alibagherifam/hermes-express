package dev.alibagherifam.hermesexpress.customer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.alibagherifam.hermesexpress.common.ui.LocalizationPreviews
import dev.alibagherifam.hermesexpress.common.ui.theme.HermesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HermesTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    Surface {
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "Hello World!",
                Modifier.align(Alignment.Center),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@LocalizationPreviews
@Composable
fun MainScreenPreview() {
    HermesTheme {
        MainScreen()
    }
}
