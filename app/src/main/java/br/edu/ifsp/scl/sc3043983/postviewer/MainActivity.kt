package br.edu.ifsp.scl.sc3043983.postviewer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import br.edu.ifsp.scl.sc3043983.postviewer.ui.navigation.Routes.PostViewerNavHost
import br.edu.ifsp.scl.sc3043983.postviewer.ui.theme.PostViewerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PostViewerTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PostViewerNavHost()
                }
            }
        }
    }
}