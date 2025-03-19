package com.hnc.company.lototools.presentation.detailsplayer

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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.hnc.company.lototools.base.composetheme.BaseTheme
import com.hnc.company.lototools.base.composetheme.scafold.BaseScaffold
import com.hnc.company.lototools.base.composetheme.text.BaseText
import com.hnc.company.lototools.base.composetheme.text.TextType
import com.hnc.company.lototools.base.mvi.BaseScreen
import com.hnc.company.lototools.domain.entity.Player
import com.hnc.company.lototools.domain.entity.Transaction
import com.hnc.company.lototools.presentation.player.ContentView
import com.hnc.company.lototools.utils.formatMoneyComma

@Composable
fun PlayerDetailScreen(
    navController: NavController,
    playerId: String,
    viewModel: PlayerDetailsViewModel = hiltViewModel()
) {
    val transaction = viewModel.transaction.collectAsStateWithLifecycle().value
    val player = viewModel.player.collectAsStateWithLifecycle().value
    BaseScreen(
        navigation = navController,
        viewModel = viewModel,
        initData = {
            viewModel.initData(playerId)
        },
        handleContent = { state ->
            PlayerDetailContent(transaction, player)
        }
    )
}

@Composable
fun PlayerDetailContent(
    transaction: List<Transaction>,
    player: Player?
) {
    BaseScaffold(modifier = Modifier.fillMaxSize(),
        topBarView = {
            Text(
                text = "Lịch sử",
                style = MaterialTheme.typography.h4.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(16.dp)
            )
        },
        bottomBar = {},
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(BaseTheme.colors.purplePrimary)
                    .padding(16.dp)
            ) {

                Spacer(modifier = Modifier.height(30.dp))

                player?.let {
                    UserView(it)
                }

                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(modifier = Modifier.fillMaxWidth()) {
                        itemsIndexed(transaction) { index, transaction ->
                            TransactionRow(index + 1, transaction)
                        }
                    }
                }
            }
        })
}

@Composable
fun UserView(player: Player) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.Bottom
    ) {

        Spacer(Modifier.weight(1f))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box {
                AvatarPodium(
                    player = player,
                    podiumHeight = 100.dp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            BaseText(
                text = player.name,
                color = Color.White,
                type = TextType.BODY1
            )

            Spacer(modifier = Modifier.height(5.dp))

            BaseText(
                text = "${player.amount.formatMoneyComma()}đ",
                color = Color.White,
                type = TextType.BODY_MEDIUM
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun AvatarPodium(
    player: Player,
    podiumHeight: Dp
) {
    Box(
        modifier = Modifier.width(100.dp)
    ) {

        Box(
            modifier = Modifier
                .height(podiumHeight)
                .clip(CircleShape)
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .background(
                    color = BaseTheme.colors.purpleDark,
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
                .size(80.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
                .align(Alignment.Center),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun TransactionRow(
    index: Int,
    transaction: Transaction
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(Color.White.copy(alpha = 0.1f), shape = MaterialTheme.shapes.small)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hạng
        BaseText(
            text = "$index. ${transaction.date}",
            color = Color.White,
            type = TextType.BODY_MEDIUM,
            modifier = Modifier.wrapContentWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        // Hạng
        BaseText(
            text = "${transaction.playType} | ${transaction.number} | ${transaction.profit.formatMoneyComma()}đ",
            color = Color.White,
            type = TextType.BODY_MEDIUM,
            modifier = Modifier.wrapContentWidth()
        )

    }
}
