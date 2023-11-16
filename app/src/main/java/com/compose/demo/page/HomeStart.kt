package com.compose.demo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.demo.R
import com.compose.demo.widget.Space
import com.compose.demo.widget.TextTab
import com.compose.demo.widget.WrapContentTabRow
import com.compose.demo.widget.indicatorOffset
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

/**
 *
 * @author zrh
 * @date 2023/11/15
 *
 */
@Preview(widthDp = 375, heightDp = 812)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeStartPage() {
    val pagerState = rememberPagerState(pageCount = 3, initialPage = 1, initialOffscreenLimit = 3)
    Box(
        modifier = Modifier.fillMaxSize().background(Color(0xFFF2F2F2))
    ) {
        Image(
            painter = painterResource(id = R.mipmap.img_page_background),
            contentDescription = "",
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        Column(
            modifier = Modifier.fillMaxSize().systemBarsPadding()
        ) {
            Row(
                Modifier.fillMaxWidth().wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Space(width = 8.dp)
                HomeStartTabs(pagerState, Modifier.height(44.dp).wrapContentSize())
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.mipmap.ic_room), contentDescription = "")
                Image(painter = painterResource(id = R.mipmap.ic_search), contentDescription = "")
                Space(width = 8.dp)
            }
            HorizontalPager(
                state = pagerState, modifier = Modifier.fillMaxWidth().weight(1f)
            ) { page ->
                HotRoomListPage()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomeStartTabs(pagerState: PagerState, modifier: Modifier = Modifier) {
    val list = remember { listOf("Mine", "Hot", "Discover") }
    val scope = rememberCoroutineScope()
    WrapContentTabRow(indicator = {
        Box(
            Modifier
                .indicatorOffset(pagerState, it, 16.dp)
                .height(3.dp)
                .background(color = Color.White, shape = RoundedCornerShape(3.dp))
        )
    }, modifier = modifier) {
        list.forEachIndexed { index, item ->
            val isSelected = index == pagerState.currentPage
            val textSize = if (isSelected) 18.sp else 16.sp
            TextTab(text = item, textSize = textSize, modifier = Modifier.padding(8.dp)) {
                scope.launch { pagerState.animateScrollToPage(index) }
            }
        }
    }
}