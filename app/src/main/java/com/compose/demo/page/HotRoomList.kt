package com.compose.demo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.demo.widget.Space
import com.compose.demo.R
import com.compose.demo.route.LocalNavController
import com.compose.demo.route.RoutePath
import com.compose.demo.route.getNavController
import com.compose.demo.widget.onClick

/**
 *
 * @author zrh
 * @date 2023/11/15
 *
 */
@Preview(widthDp = 375, heightDp = 812)
@Composable
fun HotRoomListPage() {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item { Banner() }
        item { ActivityCards() }
        items(5) {
            RoomCardRow()
        }
        item { Space(height = 10.dp) }
    }
}

@Composable
fun Banner() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
    ) {
        Space(width = 10.dp)
        Box(
            modifier = Modifier
                .weight(1f)
                .aspectRatio(355f / 97)
                .background(Color.Gray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
        )
        Space(width = 10.dp)
    }
}

@Composable
fun ActivityCards() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
    ) {
        Image(
            painter = painterResource(id = R.mipmap.img_cp_card),
            contentDescription = "",
            modifier = Modifier.weight(1f).aspectRatio(186f / 76)
        )
        Image(
            painter = painterResource(id = R.mipmap.img_ranking_card),
            contentDescription = "",
            modifier = Modifier.weight(1f).aspectRatio(186f / 76)
        )
    }
}

@Composable
fun RoomCardRow() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(top = 10.dp)
    ) {
        Space(width = 10.dp)
        RoomCard(modifier = Modifier.weight(1f))
        Space(width = 10.dp)
        RoomCard(modifier = Modifier.weight(1f))
        Space(width = 10.dp)
    }
}

@Composable
fun RoomCard(modifier: Modifier) {
    val navHostController = getNavController()
    Box(modifier = modifier.background(Color.White, RoundedCornerShape(8.dp))) {
        Column(modifier = Modifier.fillMaxWidth().onClick {
            navHostController.navigate(RoutePath.Room)
        }) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(Color.Gray, RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            )
            Space(height = 10.dp)
            Text(
                text = "🇺🇸🇺🇸🇺🇸🇺🇸",
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Space(height = 5.dp)
            Text(
                text = "睡觉睡觉数理逻辑睡觉睡觉数理逻辑睡觉睡觉数理逻辑",
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 10.dp)
            )
            Space(height = 5.dp)
        }

        Row(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset((-10).dp, 10.dp)
                .height(18.dp)
                .width(50.dp)
                .background(Color(0x80E2E2E2), RoundedCornerShape(18.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Space(5.dp)
            Image(painter = painterResource(id = R.mipmap.img_sound), contentDescription = "")
            Text(
                text = "123",
                fontSize = 12.sp,
                color = Color.White,
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
        }
    }
}