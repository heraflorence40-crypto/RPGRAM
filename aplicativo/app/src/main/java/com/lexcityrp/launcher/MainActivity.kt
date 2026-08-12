package com.lexcityrp.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { LauncherScreen() }
    }
}

@Composable
private fun LauncherScreen() {
    Column(
        modifier = Modifier.fillMaxSize().background(Color(0xFF070707)).padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("LEX CITY RP", style = MaterialTheme.typography.displaySmall, color = Color.White)
        Text("Launcher Android", color = Color(0xFF00B8FF), modifier = Modifier.padding(top = 8.dp))
        Text("Versão 1.0 • pronto para jogar", color = Color.LightGray, modifier = Modifier.padding(vertical = 24.dp))
        Button(onClick = { }, modifier = Modifier.fillMaxWidth()) { Text("JOGAR") }
        Button(onClick = { }, modifier = Modifier.fillMaxWidth().padding(top = 12.dp)) { Text("CONFIGURAÇÕES") }
    }
}
