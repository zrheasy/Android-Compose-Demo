package com.compose.demo.widget

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import kotlin.math.absoluteValue
import kotlin.math.max

/**
 *
 * @author zrh
 * @date 2023/11/15
 *
 */

@Composable
fun WrapContentTabRow(
    modifier: Modifier = Modifier,
    indicator: @Composable (tabPositions: List<TabPosition>) -> Unit = @Composable { },
    tabs: @Composable () -> Unit
) {

    SubcomposeLayout(modifier.wrapContentWidth()) { constraints ->
        val tabPlaceables = subcompose(TabSlots.Tabs, tabs).map {
            val width = it.minIntrinsicWidth(constraints.maxHeight)
            it.measure(constraints.copy(minWidth = width, maxWidth = width))
        }

        var layoutWidth = 0
        var layoutHeight = 0
        tabPlaceables.forEach {
            layoutWidth += it.width
            layoutHeight = maxOf(layoutHeight, it.height)
        }

        // Position the children.
        layout(layoutWidth, layoutHeight) {
            // Place the tabs
            val tabPositions = mutableListOf<TabPosition>()
            var left = 0
            tabPlaceables.forEach {
                it.placeRelative(left, 0)
                tabPositions.add(TabPosition(left = left.toDp(), width = it.width.toDp()))
                left += it.width
            }

            // The indicator container is measured to fill the entire space occupied by the tab
            // row, and then placed on top of the divider.
            subcompose(TabSlots.Indicator) { indicator(tabPositions) }.forEach {
                it.measure(Constraints.fixed(layoutWidth, layoutHeight)).placeRelative(0, 0)
            }
        }
    }
}

@Immutable
class TabPosition internal constructor(val left: Dp, val width: Dp) {
    val right: Dp get() = left + width

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TabPosition) return false

        if (left != other.left) return false
        if (width != other.width) return false

        return true
    }

    override fun hashCode(): Int {
        var result = left.hashCode()
        result = 31 * result + width.hashCode()
        return result
    }

    override fun toString(): String {
        return "TabPosition(left=$left, right=$right, width=$width)"
    }
}

private enum class TabSlots {
    Tabs,
    Indicator
}

@SuppressLint("UnnecessaryComposedModifier")
@ExperimentalPagerApi
fun Modifier.indicatorOffset(
    pagerState: PagerState,
    tabPositions: List<TabPosition>,
    width: Dp
): Modifier = composed {
    val targetIndicatorOffset: Dp

    val currentTab = tabPositions[pagerState.currentPage]
    val targetPage = pagerState.targetPage
    val targetTab = targetPage?.let { tabPositions.getOrNull(it) }
    val currStart = currentTab.left + (currentTab.width - width) / 2
    if (targetTab != null) {
        val targetDistance = (targetPage - pagerState.currentPage).absoluteValue
        val fraction = (pagerState.currentPageOffset / max(targetDistance, 1)).absoluteValue
        val targetStart = targetTab.left + (targetTab.width - width) / 2
        targetIndicatorOffset = lerp(currStart, targetStart, fraction)
    } else {
        targetIndicatorOffset = currStart
    }

    wrapContentSize(Alignment.BottomStart)
        .offset(x = targetIndicatorOffset)
        .width(width)
}

@Composable
fun TextTab(text: String, modifier: Modifier = Modifier, textSize: TextUnit = 14.sp, textColor: Color = Color.White, onClick: () -> Unit = {}) {
    Box(modifier = Modifier
        .clickable(onClick = onClick)
        .fillMaxHeight()
        .wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = textSize, color = textColor, modifier = modifier)
    }
}