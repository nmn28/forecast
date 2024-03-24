package com.example.forecast.ui.ai

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.forecast.ui.ai.ui.common.AppBar
import com.example.forecast.ui.ai.ui.common.AppScaffold
import com.example.forecast.ui.ai.ui.conversations.Conversation
import com.example.forecast.ui.ai.ui.conversations.components.TextInput
import com.example.forecast.ui.ai.ui.theme.ChatGPTLiteTheme
import kotlinx.coroutines.launch

@Composable
fun aiScreen(aiViewModel: aiViewModel = hiltViewModel()) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val drawerOpen by aiViewModel.drawerShouldBeOpened.collectAsState()

    if (drawerOpen) {
        LaunchedEffect(Unit) {
            try {
                drawerState.open()
            } finally {
                aiViewModel.resetOpenDrawerAction()
            }
        }
    }

    val scope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    BackHandler(enabled = drawerState.isOpen) {
        scope.launch {
            drawerState.close()
        }
    }

    val darkTheme = remember { mutableStateOf(true) }

    ChatGPTLiteTheme(darkTheme = darkTheme.value) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.fillMaxSize()) {
                Conversation()
                TextInput()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ChatGPTLiteTheme {
        aiScreen()
    }
}