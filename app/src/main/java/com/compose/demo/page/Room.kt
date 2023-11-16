package com.compose.demo.page

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.compose.demo.R
import com.compose.demo.entity.RoomMessageEntity
import com.compose.demo.route.getNavController
import com.compose.demo.widget.Space
import com.compose.demo.widget.onClick
import com.compose.demo.widget.rememberStateOf
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 *
 * @author zrh
 * @date 2023/11/16
 *
 */
@OptIn(ExperimentalLayoutApi::class)
@Preview(widthDp = 375, heightDp = 812)
@Composable
fun RoomPage() {
    val navHostController = getNavController()
    var showExitWindow by rememberStateOf(value = false)
    var showInput by rememberStateOf(value = false)

    val messageList = remember {
        mutableStateListOf<RoomMessageEntity>().apply {
            add(RoomMessageEntity("Welcome to Yalla. Please respect each otherand chat in a decent manner"))
        }
    }

    val listState = rememberLazyListState()
    LaunchedEffect(messageList.size) {
        listState.animateScrollToItem(messageList.lastIndex)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.mipmap.img_room_bg),
            contentDescription = "",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        ConstraintLayout(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
        ) {
            val (roomInfoLayout, menuLayout, contributionLayout, onlineLayout, seatLayout, messageLayout, inputLayout) = createRefs()

            RoomInfoLayout(modifier = Modifier.constrainAs(roomInfoLayout) {
                top.linkTo(parent.top, margin = 7.dp)
                start.linkTo(parent.start)
            })

            RoomMenuLayout(modifier = Modifier.constrainAs(menuLayout) {
                top.linkTo(roomInfoLayout.top)
                bottom.linkTo(roomInfoLayout.bottom)
                end.linkTo(parent.end, margin = 20.dp)
            }) { type ->
                when (type) {
                    0 -> showExitWindow = true
                }
            }

            RoomContributionLayout(modifier = Modifier.constrainAs(contributionLayout) {
                top.linkTo(roomInfoLayout.bottom, margin = 15.dp)
                start.linkTo(parent.start)
            })

            RoomOnlineLayout(modifier = Modifier.constrainAs(onlineLayout) {
                top.linkTo(roomInfoLayout.bottom)
                end.linkTo(parent.end, margin = 17.dp)
            })

            RoomSeatLayout(modifier = Modifier.constrainAs(seatLayout) {
                top.linkTo(contributionLayout.bottom, margin = 10.dp)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            })

            RoomBottomLayout(modifier = Modifier.constrainAs(inputLayout) {
                bottom.linkTo(parent.bottom, margin = 10.dp)
            }) {
                when (it) {
                    0 -> showInput = true
                    1 -> {
                        messageList.add(RoomMessageEntity("😄😄😄"))
                    }
                }
            }

            RoomMessageLayout(messageList,
                              listState,
                              modifier = Modifier.constrainAs(messageLayout) {
                                  top.linkTo(seatLayout.bottom)
                                  start.linkTo(parent.start)
                                  end.linkTo(parent.end)
                                  bottom.linkTo(inputLayout.top, margin = 10.dp)
                                  width = Dimension.fillToConstraints
                                  height = Dimension.fillToConstraints
                              })
        }

        RoomExitLayout(showExitWindow = showExitWindow) { type ->
            when (type) {
                1 -> navHostController.popBackStack()
                else -> showExitWindow = false
            }
        }

        BackHandler(true) {
            showExitWindow = !showExitWindow
        }

        RoomInputLayout(showInput) {
            if (it.isNotBlank()) {
                messageList.add(RoomMessageEntity(it))
            }
        }

        val imeVisible = WindowInsets.isImeVisible
        LaunchedEffect(imeVisible) {
            if (!imeVisible) {
                showInput = false
            }
        }
    }
}

@Composable
fun RoomExitLayout(showExitWindow: Boolean, onClick: (type: Int) -> Unit) {

    AnimatedVisibility(visible = showExitWindow, enter = fadeIn(), exit = fadeOut()) {
        Column(
            modifier = Modifier.fillMaxSize().background(Color(0x99000000)).onClick { onClick(0) },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(painter = painterResource(id = R.mipmap.ic_room_exit),
                  contentDescription = "",
                  modifier = Modifier.size(92.dp).onClick { onClick(1) })
            Space(height = 118.dp)
            Image(painter = painterResource(id = R.mipmap.ic_room_mini),
                  contentDescription = "",
                  modifier = Modifier.size(92.dp).onClick { onClick(1) })
        }
    }
}

@Composable
fun RoomInfoLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(51.dp).wrapContentWidth().background(
            Color(0x33FFFFFF), RoundedCornerShape(topEnd = 51.dp, bottomEnd = 51.dp)
        ), verticalAlignment = Alignment.CenterVertically
    ) {
        Space(width = 10.dp)
        Box(modifier = Modifier.size(37.dp).background(Color.White, RoundedCornerShape(5.dp)))
        Space(width = 10.dp)
        Column(modifier = Modifier.wrapContentWidth().wrapContentHeight()) {
            Text(text = "POPO", fontSize = 14.sp, color = Color.White)
            Space(height = 6.dp)
            Text(text = "ID:84647733", fontSize = 12.sp, color = Color.White)
        }
        Space(width = 7.dp)
        Image(painter = painterResource(id = R.mipmap.ic_follow_room), contentDescription = "")
        Space(width = 10.dp)
    }
}

@Composable
fun RoomMenuLayout(modifier: Modifier = Modifier, onClick: (type: Int) -> Unit) {
    Row(modifier = modifier.wrapContentSize(), verticalAlignment = Alignment.CenterVertically) {
        Image(painter = painterResource(id = R.mipmap.ic_room_close),
              contentDescription = "",
              modifier = Modifier.size(33.dp).onClick { onClick(0) })
    }
}

@Composable
fun RoomContributionLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(30.dp).wrapContentWidth().background(
            Color(0x33FFFFFF), RoundedCornerShape(topEnd = 30.dp, bottomEnd = 30.dp)
        ), verticalAlignment = Alignment.CenterVertically
    ) {
        Space(width = 10.dp)
        Image(
            painter = painterResource(id = R.mipmap.ic_room_contribution),
            contentDescription = "",
            modifier = Modifier.size(24.dp)
        )
        Space(width = 5.dp)
        Text(text = "3234", fontSize = 12.sp, color = Color(0xFFFFD500))
        Space(width = 10.dp)
        Image(
            painter = painterResource(id = R.mipmap.ic_room_contribution_arrow),
            contentDescription = ""
        )
        Space(width = 10.dp)
    }
}

@Composable
fun RoomOnlineLayout(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.height(30.dp).wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(30.dp).background(Color(0xFFFFFFFF), CircleShape))
        Space(width = 5.dp)
        Box(modifier = Modifier.size(30.dp).background(Color(0xFFFFFFFF), CircleShape))
        Space(width = 5.dp)
        Box(modifier = Modifier.size(30.dp).background(Color(0xFFFFFFFF), CircleShape))
        Space(width = 5.dp)
        Box(
            modifier = Modifier.size(30.dp).background(Color(0x33FFFFFF), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "3", color = Color.White, fontSize = 14.sp)
        }
    }
}

@Composable
fun RoomSeatLayout(modifier: Modifier = Modifier) {
    Column(modifier = modifier.wrapContentHeight()) {
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            Box(modifier = Modifier.weight(1f))
            RoomSeatItem(1, modifier = Modifier.weight(1f))
            RoomSeatItem(2, modifier = Modifier.weight(1f))
            Box(modifier = Modifier.weight(1f))
        }
        Space(height = 32.dp)
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            RoomSeatItem(3, modifier = Modifier.weight(1f))
            RoomSeatItem(4, modifier = Modifier.weight(1f))
            RoomSeatItem(5, modifier = Modifier.weight(1f))
            RoomSeatItem(6, modifier = Modifier.weight(1f))
        }
        Space(height = 32.dp)
        Row(modifier = Modifier.fillMaxWidth().wrapContentHeight()) {
            RoomSeatItem(7, modifier = Modifier.weight(1f))
            RoomSeatItem(8, modifier = Modifier.weight(1f))
            RoomSeatItem(9, modifier = Modifier.weight(1f))
            RoomSeatItem(10, modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun RoomSeatItem(position: Int, modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(52.dp).background(Color(0x33FFFFFF), CircleShape).paint(
                painter = painterResource(
                    id = R.mipmap.ic_room_seat_empty
                )
            )
        )
        Space(height = 6.dp)
        Box(
            modifier = Modifier
                .height(18.dp)
                .width(31.dp)
                .background(Color(0x33FFFFFF), RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = position.toString(), color = Color.White, fontSize = 12.sp)
        }
    }
}

@Composable
fun RoomMessageLayout(
    messageList: List<RoomMessageEntity>, listState: LazyListState, modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, listState) {
        items(messageList) {
            RoomMessageItem(item = it)
        }
    }
}

@Composable
fun RoomMessageItem(item: RoomMessageEntity) {
    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (msg) = createRefs()
        Box(modifier = Modifier
            .constrainAs(msg) {
                linkTo(
                    start = parent.start,
                    end = parent.end,
                    bias = 0f,
                    startMargin = 20.dp,
                    endMargin = 77.dp
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
                height = Dimension.wrapContent
            }
            .wrapContentSize(Alignment.TopStart)
            .background(Color(0x4D000000), RoundedCornerShape(7.dp))
            .padding(12.dp)) {
            Text(text = item.text, color = Color(0xFFD085FC), fontSize = 14.sp)
        }
    }
}

@Composable
fun RoomBottomLayout(modifier: Modifier = Modifier, onClick: (type: Int) -> Unit) {
    ConstraintLayout(modifier = modifier.wrapContentHeight().fillMaxWidth()) {
        val (gift, send, emoji, sound, message, menu) = createRefs()

        Box(modifier = Modifier.constrainAs(send) {
            start.linkTo(parent.start, margin = 10.dp)
            top.linkTo(emoji.top)
            bottom.linkTo(emoji.bottom)
            width = Dimension.percent(0.1787f)
            height = Dimension.fillToConstraints
        }.background(Color(0x4D000000), RoundedCornerShape(50.dp)).onClick { onClick(0) },
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Send...", color = Color.White, fontSize = 14.sp)
        }

        Image(painter = painterResource(id = R.mipmap.ic_room_input_emoji),
              contentDescription = "",
              modifier = Modifier.constrainAs(emoji) {
                  start.linkTo(send.end)
                  end.linkTo(sound.start)
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  width = Dimension.percent(0.0907f)
                  height = Dimension.ratio("1:1")
              }.onClick { onClick(1) })

        Image(painter = painterResource(id = R.mipmap.ic_room_input_sound),
              contentDescription = "",
              modifier = Modifier.constrainAs(sound) {
                  end.linkTo(gift.start, margin = 10.dp)
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  width = Dimension.percent(0.0907f)
                  height = Dimension.ratio("1:1")
              })

        Image(painter = painterResource(id = R.mipmap.ic_room_input_gift),
              contentDescription = "",
              modifier = Modifier.constrainAs(gift) {
                  start.linkTo(parent.start)
                  end.linkTo(parent.end)
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  width = Dimension.percent(0.1253f)
                  height = Dimension.ratio("1:1")
              })

        Image(painter = painterResource(id = R.mipmap.ic_room_input_message),
              contentDescription = "",
              modifier = Modifier.constrainAs(message) {
                  start.linkTo(gift.end, margin = 10.dp)
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  width = Dimension.percent(0.0907f)
                  height = Dimension.ratio("1:1")
              })

        Image(painter = painterResource(id = R.mipmap.ic_room_input_menu),
              contentDescription = "",
              modifier = Modifier.constrainAs(menu) {
                  end.linkTo(parent.end, margin = 20.dp)
                  top.linkTo(parent.top)
                  bottom.linkTo(parent.bottom)
                  width = Dimension.percent(0.0907f)
                  height = Dimension.ratio("1:1")
              })
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RoomInputLayout(visible: Boolean, onSubmit: (text: String) -> Unit) {
    val keyboard = LocalSoftwareKeyboardController.current

    if (visible) {
        Box(modifier = Modifier.fillMaxSize().imePadding().onClick { keyboard?.hide() }) {
            val focusRequester = remember { FocusRequester() }
            var text by rememberStateOf(value = "随便发点什么😁")
            LaunchedEffect(focusRequester) {
                delay(100)
                focusRequester.requestFocus()
                keyboard?.show()
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .background(Color.White, RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    .align(Alignment.BottomStart)
                    .onClick { }, verticalAlignment = Alignment.CenterVertically
            ) {
                Space(width = 10.dp)
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .background(Color(0xFFF7F8FC), RoundedCornerShape(19.dp))
                        .padding(horizontal = 19.dp), contentAlignment = Alignment.Center
                ) {
                    BasicTextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.fillMaxWidth().focusRequester(focusRequester)
                    )
                }
                Space(width = 10.dp)
                Image(painter = painterResource(id = R.mipmap.ic_room_send),
                      contentDescription = "",
                      modifier = Modifier.onClick {
                          keyboard?.hide()
                          onSubmit(text)
                      })
                Space(width = 10.dp)
            }
        }
    }
}
