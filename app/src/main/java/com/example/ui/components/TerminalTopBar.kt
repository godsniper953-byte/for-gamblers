package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.data.MarketRepository
import com.example.ui.theme.*
import com.example.viewmodel.Screen

private const val LOGO_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDtorrrV9sa5Lbm87qQDUnLCt9gbZ0XmCOVWiuV1n6hRK35h7jjtWFYsQjQ5p_ia8v7ASCUGTlaW4QrtuHziRMFsJ5Tw2eFvjojOMo3hTZtYD3GV0UOzIjPewep--de6PiROHFPRH6fzL6uRjQv0XKhj_QvR-lCF9WxrezzTA1xcD2lvEwy2IrzOt3Li_JT8wWCTG0PciRqzsJ9ru5emHG_k7tDOIK_ZiXqF91WkWNxcorAz_-QbGN7jg"
private const val AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDnyeD_qLKJbNe8bjgMpDKmhfILcrgvKcK4Latr678IEijlDjmUttBtZLOULrL4ZbKGOEB-KO85RRVylnGa7GumeltydXDKvGEtbtJhyLzQBsOw6LbBgOTnWyNYTcm-S7bFPGDmGTwpLIVN-F6kVHlz2fo8kIah86gK0SFm5qh331IFOZ0MR8o9aaXaSGe9rqkMuYOMIzj5TBoOTSdEOjYBdG7NHh62OKQhZaJF9oCSFbxC3cgo5hpvxg"

@Composable
fun TerminalTopBar(
    currentScreen: Screen,
    onDrawerToggle: () -> Unit,
    onSearchClick: () -> Unit,
    onNotificationsClick: () -> Unit,
    onTickerSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Pulse animation for LIVE indicator
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val alphaAnim by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "live_pulse"
    )

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(SurfaceBase)
    ) {
        // Main Header Row (Height 56dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Left Group: Drawer Button, Logo, Title & Version
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f, fill = false)
            ) {
                IconButton(
                    onClick = onDrawerToggle,
                    modifier = Modifier
                        .size(44.dp)
                        .testTag("menu_drawer_toggle")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle Navigation Drawer",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Logo
                AsyncImage(
                    model = LOGO_URL,
                    contentDescription = "EconPulse AI Terminal Logo",
                    modifier = Modifier
                        .height(28.dp)
                        .width(28.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.width(8.dp))

                Column(
                    modifier = Modifier.weight(1f, fill = false),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = when (currentScreen) {
                                Screen.Terminal -> "TERMINAL OVERVIEW"
                                Screen.Markets -> "MARKETS"
                                Screen.Commodities -> "OIL INTELLIGENCE"
                                Screen.AiAnalyst -> "AI ANALYST"
                                Screen.Research -> "RESEARCH LAB"
                            },
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.5.sp,
                                letterSpacing = (-0.01).sp
                            ),
                            color = OnSurface,
                            maxLines = 1
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh, RoundedCornerShape(3.dp))
                                .border(1.dp, TelemetryCyan.copy(alpha = 0.3f), RoundedCornerShape(3.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "PRO",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 0.08.sp
                                ),
                                color = TelemetryCyan
                            )
                        }
                    }
                    Text(
                        text = "ECONPULSE v4.8",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.5.sp,
                            color = OnSurfaceVariant
                        )
                    )
                }
            }

            // Right Group: Live NSE Pill, Search, Bell, Profile Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                // Live Badge
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerLowest, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 3.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(BullishEmerald.copy(alpha = alphaAnim))
                        )
                        Text(
                            text = "NSE: LIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 9.5.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = BullishEmerald
                        )
                    }
                }

                IconButton(
                    onClick = onSearchClick,
                    modifier = Modifier
                        .size(40.dp)
                        .testTag("search_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Tickers",
                        tint = OnSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box {
                    IconButton(
                        onClick = onNotificationsClick,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("notifications_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications",
                            tint = OnSurfaceVariant,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    // Amber Notification Dot
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 9.dp, end = 9.dp)
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(AmberAdvisory)
                    )
                }

                Spacer(modifier = Modifier.width(2.dp))

                // Profile Avatar with ring
                AsyncImage(
                    model = AVATAR_URL,
                    contentDescription = "User Profile",
                    modifier = Modifier
                        .size(30.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, TelemetryCyan.copy(alpha = 0.5f), CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Ticker Tape Ribbon (Height 36dp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .background(SurfaceContainerLowest.copy(alpha = 0.95f))
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Market status flags
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(BullishEmerald)
                )
                Text(
                    text = "NSE: OPEN",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = OnSurfaceVariant
                )
                Text("•", color = OutlineVariant, fontSize = 10.sp)
                Text(
                    text = "NYSE: PRE-MKT",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = OnSurfaceVariant
                )
                Text("•", color = OutlineVariant, fontSize = 10.sp)
                Text(
                    text = "LSE: OPEN",
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = OnSurfaceVariant
                )
            }

            Box(
                modifier = Modifier
                    .width(1.dp)
                    .height(14.dp)
                    .background(SurfaceBright)
            )

            // Individual Items
            MarketRepository.tickerTapeItems.forEach { item ->
                Row(
                    modifier = Modifier
                        .clickable { onTickerSelect(item.symbol) }
                        .padding(horizontal = 4.dp, vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = item.symbol,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Medium,
                            fontSize = 11.sp
                        ),
                        color = OnSurfaceVariant
                    )
                    Text(
                        text = item.price,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.5.sp
                        ),
                        color = OnSurface
                    )
                    Text(
                        text = item.changeText,
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 10.sp
                        ),
                        color = if (item.isPositive) BullishEmerald else BearishCrimson
                    )
                }
            }
        }
        
        HorizontalDivider(color = OutlineVariant.copy(alpha = 0.4f), thickness = 0.5.dp)
    }
}
