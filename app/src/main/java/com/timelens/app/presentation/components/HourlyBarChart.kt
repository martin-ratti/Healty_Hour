package com.timelens.app.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.timelens.app.presentation.theme.NeonBlue

@Composable
fun HourlyBarChart(
    hourlyUsageMs: Map<Int, Long>,
    modifier: Modifier = Modifier,
    barColor: Color = NeonBlue
) {
    val maxMs = remember(hourlyUsageMs) {
        hourlyUsageMs.values.maxOrNull()?.coerceAtLeast(1L) ?: 1L
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)
                .padding(top = 12.dp, bottom = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            for (hour in 0..23) {
                val ms = hourlyUsageMs[hour] ?: 0L
                val heightFraction = (ms.toFloat() / maxMs).coerceIn(0f, 1f)

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxHeight()
                            .width(6.dp)
                    ) {
                        val canvasHeight = size.height
                        val canvasWidth = size.width
                        val barHeight = (canvasHeight * heightFraction).coerceAtLeast(if (ms > 0) 6f else 0f)

                        drawRoundRect(
                            color = if (ms > 0) barColor else Color.Transparent,
                            topLeft = Offset(0f, canvasHeight - barHeight),
                            size = Size(canvasWidth, barHeight),
                            cornerRadius = CornerRadius(3f, 3f)
                        )
                    }
                }
            }
        }

        // Hour labels (0h, 6h, 12h, 18h, 23h)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("00:00", "06:00", "12:00", "18:00", "23:00").forEach { label ->
                Text(
                    text = label,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
