package com.compose.demo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.compose.demo.R
import com.compose.demo.ui.theme.SystemBars
import com.compose.demo.ui.theme.backgroundColor
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


/**
 *
 * @author zrh
 * @date 2023/11/13
 *
 */
@Preview(widthDp = 375, heightDp = 812)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun HomePage() {
    val pagerState = rememberPagerState(pageCount = 4, initialOffscreenLimit = 4)
    Column(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
    ) {
        Pages(pagerState = pagerState, modifier = Modifier
            .fillMaxWidth()
            .weight(1f))
        BottomTabs(pagerState = pagerState, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Pages(pagerState: PagerState, modifier: Modifier = Modifier) {
    SystemBars(statusBarDarkFont = pagerState.currentPage != 0)
    HorizontalPager(state = pagerState, modifier = modifier) { page ->
        if (page == 0) HomeStartPage()
        else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Page:$page")
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun BottomTabs(pagerState: PagerState, modifier: Modifier = Modifier) {
    val list = remember {
        listOf(
            R.mipmap.ic_main_home_selected to R.mipmap.ic_main_home_un_select,
            R.mipmap.ic_main_dynamic_selected to R.mipmap.ic_main_dynamic_un_select,
            R.mipmap.ic_main_message_selected to R.mipmap.ic_main_message_un_select,
            R.mipmap.ic_main_me_selected to R.mipmap.ic_main_me_un_select,
        )
    }
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.White,
        contentColor = Color.White,
        indicator = {},
        divider = {},
        modifier = modifier
    ) {
        list.forEachIndexed { index, item ->
            val isSelected = index == pagerState.currentPage
            val icon = if (isSelected) item.first else item.second
            Tab(
                selected = isSelected,
                icon = { Image(painter = painterResource(icon), contentDescription = "") },
                onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
            )
        }
    }
}