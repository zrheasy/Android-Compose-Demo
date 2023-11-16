package com.compose.demo.page

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavHostController
import com.compose.demo.R
import com.compose.demo.route.RoutePath
import com.compose.demo.route.getNavController
import com.compose.demo.ui.theme.SystemBars
import com.compose.demo.widget.onClick

/**
 *
 * @author zrh
 * @date 2023/11/10
 *
 */
@Preview(widthDp = 375, heightDp = 812)
@Composable
fun LoginPage() {
    val navController: NavHostController = getNavController()
    SystemBars(statusBarColor = Color.Transparent)
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (background, content, mask) = createRefs()
        Image(painter = painterResource(id = R.mipmap.background),
              contentDescription = "",
              modifier = Modifier.constrainAs(background) {
                  top.linkTo(parent.top)
                  width = Dimension.matchParent
                  height = Dimension.ratio("H, 375:495")
              })

        Image(painter = painterResource(id = R.mipmap.bg_mask),
              contentDescription = "",
              modifier = Modifier.constrainAs(mask) {
                  bottom.linkTo(content.top, margin = (-1).dp)
                  width = Dimension.matchParent
                  height = Dimension.ratio("H, 375:101")
              })

        Column(modifier = Modifier.constrainAs(content) {
                bottom.linkTo(parent.bottom)
                width = Dimension.matchParent
                height = Dimension.wrapContent
            }.background(Color.White)) {
            Spacer(modifier = Modifier.height(23.dp))
            Box(
                modifier = Modifier
                    .height(44.dp)
                    .fillMaxWidth(0.784f)
                    .background(color = Color(0xFF3AB0FF), shape = RoundedCornerShape(22.dp))
                    .align(Alignment.CenterHorizontally)
                    .padding(horizontal = 15.dp), contentAlignment = Alignment.CenterStart
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_fackbook_logo),
                    contentDescription = "",
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = "Login with Facebook",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            Box(modifier = Modifier
                .onClick {
                    navController.navigate(RoutePath.Home) {
                        popUpTo(RoutePath.Login) { inclusive = true }
                    }
                }
                .height(44.dp)
                .fillMaxWidth(0.784f)
                .border(
                    width = 1.dp,
                    color = Color(0xFFE6E6E6),
                    shape = RoundedCornerShape(22.dp)
                )
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 15.dp), contentAlignment = Alignment.CenterStart) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_google_logo),
                    contentDescription = "",
                    modifier = Modifier.size(26.dp)
                )
                Text(
                    text = "Login with Google",
                    fontSize = 16.sp,
                    color = Color(0xFF333333),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_phone_logo),
                    contentDescription = "",
                    modifier = Modifier.size(53.dp)
                )
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    painter = painterResource(id = R.mipmap.ic_app_logo),
                    contentDescription = "",
                    modifier = Modifier.size(53.dp)
                )
            }

            Spacer(modifier = Modifier.height(23.dp))

            Row(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Language: ", color = Color(0xFF333333), fontSize = 12.sp)
                Text(text = "English", color = Color(0xFF9768F2), fontSize = 12.sp)
                Spacer(modifier = Modifier.width(3.dp))
                Image(
                    painter = painterResource(id = R.mipmap.ic_dropdown),
                    contentDescription = "",
                    modifier = Modifier.size(6.dp).offset(y = 2.dp)
                )
            }

            Spacer(modifier = Modifier.height(27.dp))

            val isChecked = remember { mutableStateOf(false) }
            Row(modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }) {
                    isChecked.value = !isChecked.value
                }, horizontalArrangement = Arrangement.Center
            ) {
                val checkboxIcon =
                    if (isChecked.value) painterResource(id = R.mipmap.ic_checkbox_selected)
                    else painterResource(id = R.mipmap.ic_checkbox_unselect)
                Image(
                    painter = checkboxIcon,
                    contentDescription = "",
                    modifier = Modifier.size(15.dp).offset(y = 1.dp)
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = buildAnnotatedString {
                    append("Sign in and register to agree to the \n")
                    withStyle(style = SpanStyle(color = Color(0xFF9768F2))) {
                        append("terms of use & privacy policy")
                    }
                }, textAlign = TextAlign.Center, fontSize = 11.sp, color = Color(0xFF333333))
            }

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}