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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.hnc.company.lototools.R
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.text.GPackageBaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.navigation.BaseNavScreen
import com.hnc.company.lototools.utils.formatMoneyComma
import com.hnc.company.lototools.utils.nonRippleClick


@Composable
fun PlayerStatisticScreen(
    navController: NavController,
    viewModel: PlayerStatisticViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Xếp hạng", "Phân tích")

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
                text = "Thống kê",
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
                        GPackageBaseText(
                            text = title,
                            color = BaseTheme.colors.textWhite,
                            type = TextType.BODY_MEDIUM,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            }

            when (selectedTab) {
                0 -> {
                    TopThreeSection(navController, topThree)
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(modifier = Modifier.weight(1f)) {
                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            itemsIndexed(others) { index, user ->
                                val rank = index + 4
                                LeaderboardRow(
                                    player = user,
                                    rank = rank
                                ) {
                                    navController.navigate(
                                        BaseNavScreen.PlayerDetailScreen.route.plus(
                                            "/${user.playerId}"
                                        )
                                    )
                                }
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
fun TopThreeSection(navController: NavController, top3: List<Player>) {
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
            Column(modifier = Modifier.nonRippleClick {
                navController.navigate(BaseNavScreen.PlayerDetailScreen.route.plus("/${top3[1].playerId}"))
            }, horizontalAlignment = Alignment.CenterHorizontally) {

                GPackageBaseText(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFC0C0C0))
                        .padding(horizontal = 10.dp),
                    text = "2",
                    color = Color.White,
                    type = TextType.BODY3
                )

                Spacer(modifier = Modifier.height(2.dp))

                AvatarPodium(
                    player = top3[1],
                    rank = 2,
                    podiumHeight = 60.dp
                )

                Spacer(modifier = Modifier.height(8.dp))

                GPackageBaseText(
                    text = top3[1].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[1].amount.formatMoneyComma()}đ",
                    color = Color.White,
                    type = TextType.BODY_SMALL
                )


            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // #1
        if (has1) {
            Column(modifier = Modifier.nonRippleClick {
                navController.navigate(BaseNavScreen.PlayerDetailScreen.route.plus("/${top3[0].playerId}"))
            }, horizontalAlignment = Alignment.CenterHorizontally) {

                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_champion),
                    modifier = Modifier
                        .size(30.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.height(2.dp))

                Box {
                    AvatarPodium(
                        player = top3[0],
                        rank = 1,
                        podiumHeight = 80.dp // podium cao nhất
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                GPackageBaseText(
                    text = top3[0].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[0].amount.formatMoneyComma()}đ",
                    color = Color.White,
                    type = TextType.BODY_SMALL
                )
            }
        } else {
            Spacer(Modifier.weight(1f))
        }

        // #3
        if (has3) {
            Column(modifier = Modifier.nonRippleClick {
                navController.navigate(BaseNavScreen.PlayerDetailScreen.route.plus("/${top3[2].playerId}"))
            }, horizontalAlignment = Alignment.CenterHorizontally) {

                GPackageBaseText(
                    modifier = Modifier
                        .clip(RoundedCornerShape(3.dp))
                        .background(Color(0xFFCD7F32))
                        .padding(horizontal = 10.dp),
                    text = "3",
                    color = Color.White,
                    type = TextType.BODY3
                )

                Spacer(modifier = Modifier.height(2.dp))

                AvatarPodium(
                    player = top3[2],
                    rank = 3,
                    podiumHeight = 60.dp
                )

                Spacer(modifier = Modifier.height(8.dp))

                GPackageBaseText(
                    text = top3[2].name,
                    color = Color.White,
                    type = TextType.BODY_MEDIUM
                )

                GPackageBaseText(
                    text = "${top3[2].amount.formatMoneyComma()}đ",
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
        modifier = Modifier.width(if (rank == 1) 80.dp else 60.dp)
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
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(player.avatar)
                    .size(Size.ORIGINAL)
                    .build()
            ),
            contentDescription = "Avatar of ${player.name}",
            modifier = Modifier
                .size(if (rank == 1) 75.dp else 58.dp)
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
    rank: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(alpha = 0.1f), shape = MaterialTheme.shapes.small)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .nonRippleClick {
                onClick.invoke()
            },
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
            text = "${player.amount.formatMoneyComma()}đ",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
    }
}
