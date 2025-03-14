package com.hnc.company.lototools.presentation.player

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.text.GPackageBaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.domain.entity.Player


@Composable
fun PlayerStatisticScreen(
    navController: NavController,
    viewModel: PlayerStatisticViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Xếp hạng", "Tổng")

    val players = viewModel.players.collectAsStateWithLifecycle().value

    val sortedPlayers = players.sortedByDescending { it.amount }
    val topThree = sortedPlayers.take(3)
    val others = sortedPlayers.drop(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BaseTheme.colors.background)
    ) {
        // Phần nền tím + nội dung
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BaseTheme.colors.purplePrimary)
                .padding(16.dp)
        ) {
            // Tiêu đề
            Text(
                text = "Leaderboard",
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            // TabRow
            TabRow(
                modifier = Modifier.clip(RoundedCornerShape(30.dp)),
                selectedTabIndex = selectedTab,
                backgroundColor = BaseTheme.colors.purpleDark,
                contentColor = Color.White
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index }
                    ) {
                        Text(
                            text = title,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    TopThreeSection(topThree)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(others) { index, user ->
                                val rank = index + 4
                                LeaderboardRow(player = user, rank = rank)
                            }
                        }
                    }
                }

                1 -> {
                    Text(
                        text = "Tổng (thống kê khác...)",
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun TopThreeSection(top3: List<Player>) {
    if (top3.isEmpty()) return
    val has1 = top3.size >= 1
    val has2 = top3.size >= 2
    val has3 = top3.size >= 3

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {
        // #2
        if (has2) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AvatarPodium(
                    player = top3[1],
                    rank = 2,
                    podiumHeight = 80.dp
                )

                GPackageBaseText(
                    text = top3[1].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[1].amount} QP",
                    color = Color.White,
                    type = TextType.BODY_SMALL
                )


            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // #1
        if (has1) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box {
                    AvatarPodium(
                        player = top3[0],
                        rank = 1,
                        podiumHeight = 100.dp // podium cao nhất
                    )
                    // Có thể chèn icon vương miện ở đây (nếu muốn)
                }
                GPackageBaseText(
                    text = top3[0].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[0].amount} QP",
                    color = Color.White,
                    type = TextType.BODY_SMALL
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // #3
        if (has3) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                AvatarPodium(
                    player = top3[2],
                    rank = 3,
                    podiumHeight = 80.dp
                )
                GPackageBaseText(
                    text = top3[2].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[2].amount} QP",
                    color = Color.White,
                    type = TextType.BODY_SMALL
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun AvatarPodium(
    player: Player,
    rank: Int,
    podiumHeight: Dp
) {
    // Hiển thị podium + avatar
    Box(
        modifier = Modifier.width(if (rank == 1) 100.dp else 80.dp)
    ) {

        Box(
            modifier = Modifier
                .height(podiumHeight)
                .clip(CircleShape)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(
                    color = when (rank) {
                        1 -> Color(0xFFFFD700) // vàng
                        2 -> Color(0xFFC0C0C0) // bạc
                        3 -> Color(0xFFCD7F32) // đồng
                        else -> Color.Gray
                    },
                    shape = MaterialTheme.shapes.medium
                )
        )

        Image(
            painter = rememberAsyncImagePainter(player.avatar),
            contentDescription = "Avatar of ${player.name}",
            modifier = Modifier
                .size(if (rank == 1) 80.dp else 56.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun LeaderboardRow(
    player: Player,
    rank: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(alpha = 0.1f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hạng
        GPackageBaseText(
            text = rank.toString(),
            color = Color.White,
            type = TextType.BODY_MEDIUM,
            modifier = Modifier.width(32.dp)
        )

        // Avatar
        Image(
            painter = rememberAsyncImagePainter(player.avatar),
            contentDescription = "Avatar of ${player.name}",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White, CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(8.dp))

        // Tên
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = player.name,
                color = Color.White,
                style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.SemiBold)
            )
            // Nếu muốn hiển thị gì đó phụ, bạn thêm ở đây
        }

        // Điểm
        Text(
            text = "${player.amount} pts",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
    }
}
