package com.rpgram.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF08090D)
private val Card = Color(0xFF12141B)
private val Pink = Color(0xFFFF2D86)
private val Purple = Color(0xFF7C3AED)
private val Blue = Color(0xFF2563EB)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { RPGRAMApp() }
    }
}

@Composable
fun RPGRAMApp() {
    var tab by remember { mutableIntStateOf(0) }
    var liked by remember { mutableStateOf(false) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            background = Bg,
            surface = Card,
            primary = Pink,
            secondary = Purple
        )
    ) {
        Scaffold(
            containerColor = Bg,
            topBar = {
                Row(
                    Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "RPGRAM",
                        fontSize = 27.sp,
                        fontWeight = FontWeight.ExtraBold,
                        style = LocalTextStyle.current.copy(
                            brush = Brush.linearGradient(listOf(Pink, Purple, Blue))
                        )
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { tab = 3 }) {
                        Icon(Icons.Default.FavoriteBorder, "Notificações")
                    }
                    IconButton(onClick = { tab = 4 }) {
                        Icon(Icons.Default.Send, "Mensagens")
                    }
                }
            },
            bottomBar = {
                NavigationBar(containerColor = Color(0xFF0D0F15)) {
                    val icons = listOf(
                        Icons.Default.Home,
                        Icons.Default.Search,
                        Icons.Default.AddBox,
                        Icons.Default.Notifications,
                        Icons.Default.Person
                    )
                    val labels = listOf("Início", "Explorar", "Publicar", "Avisos", "Perfil")
                    icons.forEachIndexed { i, icon ->
                        NavigationBarItem(
                            selected = tab == i,
                            onClick = { tab = i },
                            icon = { Icon(icon, labels[i]) },
                            label = { Text(labels[i]) }
                        )
                    }
                }
            }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                when (tab) {
                    0 -> FeedScreen(liked) { liked = !liked }
                    1 -> ExploreScreen()
                    2 -> CreateScreen()
                    3 -> NotificationsScreen()
                    else -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun FeedScreen(liked: Boolean, onLike: () -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                listOf("Você", "ana", "joao", "bia", "mateus").forEach {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            Modifier.size(62.dp).clip(CircleShape).background(
                                Brush.linearGradient(listOf(Pink, Purple, Blue))
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(it.take(1).uppercase(), fontWeight = FontWeight.Bold)
                        }
                        Text(it, fontSize = 11.sp)
                    }
                }
            }
        }
        item {
            PostCard(
                user = "larissamoreira",
                location = "São Paulo, SP",
                liked = liked,
                onLike = onLike
            )
        }
    }
}

@Composable
fun PostCard(user: String, location: String, liked: Boolean, onLike: () -> Unit) {
    Column(Modifier.padding(bottom = 18.dp)) {
        Row(
            Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(42.dp).clip(CircleShape).background(
                    Brush.linearGradient(listOf(Pink, Purple))
                ),
                contentAlignment = Alignment.Center
            ) { Text(user.take(1).uppercase(), fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(10.dp))
            Column {
                Text(user, fontWeight = FontWeight.Bold)
                Text(location, fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(Modifier.weight(1f))
            Text("•••")
        }

        Box(
            Modifier.fillMaxWidth().height(340.dp).background(
                Brush.linearGradient(
                    listOf(Color(0xFF143A55), Color(0xFF7D4A4A), Color(0xFF1E7C78))
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            Text("📸", fontSize = 70.sp)
        }

        Row(Modifier.padding(horizontal = 10.dp, vertical = 8.dp)) {
            IconButton(onClick = onLike) {
                Icon(
                    if (liked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    "Curtir",
                    tint = if (liked) Pink else Color.White
                )
            }
            IconButton(onClick = {}) { Icon(Icons.Default.ChatBubbleOutline, "Comentar") }
            IconButton(onClick = {}) { Icon(Icons.Default.Send, "Compartilhar") }
            Spacer(Modifier.weight(1f))
            IconButton(onClick = {}) { Icon(Icons.Default.BookmarkBorder, "Salvar") }
        }
        Text(
            if (liked) "Você e outras 129 pessoas curtiram" else "128 pessoas curtiram",
            Modifier.padding(horizontal = 14.dp),
            fontWeight = FontWeight.Bold
        )
        Text(
            "larissamoreira Dias leves ✨",
            Modifier.padding(horizontal = 14.dp, vertical = 4.dp)
        )
        Text(
            "Ver todos os 12 comentários",
            Modifier.padding(horizontal = 14.dp),
            color = Color.Gray
        )
    }
}

@Composable
fun ExploreScreen() {
    Column(Modifier.fillMaxSize().padding(14.dp)) {
        var query by remember { mutableStateOf("") }
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Pesquisar no RPGRAM") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            singleLine = true
        )
        Spacer(Modifier.height(16.dp))
        Text("Explorar", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))
        val posts = listOf("🌊", "🌴", "🏔️", "🍜", "🌅", "🎆", "🏙️", "🌿", "📷")
        Column {
            posts.chunked(3).forEach { row ->
                Row(Modifier.fillMaxWidth()) {
                    row.forEach {
                        Box(
                            Modifier.weight(1f).aspectRatio(1f).padding(2.dp)
                                .background(Color(0xFF1B1E27)),
                            contentAlignment = Alignment.Center
                        ) { Text(it, fontSize = 42.sp) }
                    }
                }
            }
        }
    }
}

@Composable
fun CreateScreen() {
    Column(
        Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Nova publicação", fontSize = 25.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(24.dp))
        Box(
            Modifier.fillMaxWidth().height(300.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(Color(0xFF151822)),
            contentAlignment = Alignment.Center
        ) {
            Text("📷\n\nSelecione uma foto ou vídeo", textAlign = androidx.compose.ui.text.style.TextAlign.Center)
        }
        Spacer(Modifier.height(20.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Pink)
        ) { Text("PUBLICAR", fontWeight = FontWeight.Bold) }
    }
}

@Composable
fun NotificationsScreen() {
    val notifications = listOf(
        "ana.souza curtiu sua publicação",
        "joaooctavio comentou: Linda foto! 😍",
        "beatrizalves começou a seguir você",
        "mateusribeiro curtiu sua publicação"
    )
    LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
        item { Text("Notificações", fontSize = 25.sp, fontWeight = FontWeight.Bold); Spacer(Modifier.height(16.dp)) }
        items(notifications) { n ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    Modifier.size(46.dp).clip(CircleShape).background(
                        Brush.linearGradient(listOf(Pink, Purple))
                    ),
                    contentAlignment = Alignment.Center
                ) { Text("👤") }
                Spacer(Modifier.width(12.dp))
                Text(n, Modifier.weight(1f))
                Text("2 min", color = Color.Gray, fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun ProfileScreen() {
    Column(Modifier.fillMaxSize().padding(16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(88.dp).clip(CircleShape).background(
                    Brush.linearGradient(listOf(Pink, Purple, Blue))
                ),
                contentAlignment = Alignment.Center
            ) { Text("G", fontSize = 32.sp, fontWeight = FontWeight.Bold) }
            Spacer(Modifier.width(18.dp))
            Column {
                Text("Gabriel Lima", fontSize = 21.sp, fontWeight = FontWeight.Bold)
                Text("42 posts   1.284 seguidores   620 seguindo", color = Color.Gray)
            }
        }
        Spacer(Modifier.height(18.dp))
        Text("Fotógrafo | Viajante | Apaixonado por natureza")
        Spacer(Modifier.height(14.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) { Text("Editar perfil") }
        Spacer(Modifier.height(18.dp))
        Text("Publicações", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))
        Row(Modifier.fillMaxWidth()) {
            listOf("🏞️", "🌊", "🏔️").forEach {
                Box(
                    Modifier.weight(1f).aspectRatio(1f).padding(2.dp)
                        .background(Color(0xFF1B1E27)),
                    contentAlignment = Alignment.Center
                ) { Text(it, fontSize = 40.sp) }
            }
        }
    }
}
