package com.amazonaws.matches

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.net.toUri

@Composable
fun VideoPlayerScreen(url: String) {
    Box(modifier = Modifier.fillMaxSize()) {
        VideoPlayer(uri = url.toUri())
    }
}