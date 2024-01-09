package fr.yopox.todotxt

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import kotlin.math.min

data class Task(
    val number: Int,
    val priority: Char?,
    val text: String,
) {
    fun containerColor(scheme: ColorScheme): Color = when (priority) {
        null -> scheme.surfaceColorAtElevation(1.dp)
        'A' -> scheme.tertiaryContainer
        else -> scheme.surfaceColorAtElevation((min('F'.code - priority.code, 0) * 50 + 1).dp)
    }
}

@Composable
fun TasksList(filter: (Task) -> Boolean = { true }) {
    val colorScheme = JetpackUtils.getScheme()
    val tasks = TasksData.tasks.filter(filter)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = colorScheme.background
    ) {
        Column {
            Text(
                text = "${tasks.size} Tasks",
                fontFamily = JetpackUtils.jbMono,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.displaySmall,
                color = colorScheme.primary,
                modifier = Modifier
                    .padding(16.dp, 16.dp, 0.dp, 0.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp, 0.dp, 16.dp, 0.dp)
            ) {
                tasks
                    .sortedBy { it.priority?.code ?: ('Z'.code + 1) }
                    .forEach { task ->
                        item {
                            TaskItem(state = task, colorScheme)
                        }
                    }
            }
        }
    }
}

@Composable
fun TaskItem(state: Task, colorScheme: ColorScheme = MaterialTheme.colorScheme) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = state.containerColor(colorScheme)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 4.dp)
            .background(colorScheme.primaryContainer, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(),
                onClick = {}
            )
    ) {
        Row {
            Text(
                text = when (val n = state.number) {
                    in 0..9 -> "0$n"
                    else -> n.toString()
                },
                fontFamily = JetpackUtils.jbMono,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyLarge,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(12.dp, 8.dp)
            )
            Text(
                text = state.text,
                fontFamily = JetpackUtils.jbMono,
                color = colorScheme.onPrimaryContainer,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(0.dp, 4.dp, 8.dp, 4.dp)
            )
        }
    }
}