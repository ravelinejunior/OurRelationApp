package com.raveline.ourrelationsapp.ui.screen.swipeScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.raveline.ourrelationsapp.R
import com.raveline.ourrelationsapp.ui.common.components.Direction
import com.raveline.ourrelationsapp.ui.common.components.rememberSwipeableCardState
import com.raveline.ourrelationsapp.ui.common.components.swipableCard
import com.raveline.ourrelationsapp.ui.domain.models.MatchProfile
import com.raveline.ourrelationsapp.ui.domain.models.profiles
import kotlinx.coroutines.launch

@Composable
fun SwipeScreen() {
    SwipeCards()
}

@Composable
fun SwipeCards() {
    TransparentSystemBars()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color(0xFFEC454B),
                        Color(0xFF2979FF),
                        Color.Black,
                        Color(0xFF2979FF),

                    )
                )
            )
    ) {
        /**
         * A composable that contains a swipeable card and buttons to control it.
         */
        Box {
            val states = profiles.reversed()
                .map { it to rememberSwipeableCardState() }

            var hint by remember {
                mutableStateOf("Swipe a card or press a button below")
            }

            val scope = rememberCoroutineScope()

            Hint(hint)

            Box(
                Modifier
                    .padding(24.dp)
                    .fillMaxSize()
                    .aspectRatio(0.65f)
                    .align(Alignment.Center)
            ) {
                states.forEach { (matchProfile, state) ->
                    if (state.swipedDirection == null) {
                        ProfileCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .swipableCard(
                                    state = state,
                                    blockedDirections = listOf(Direction.Down),
                                    onSwiped = {
                                        // swipes are handled by the LaunchedEffect
                                        // so that we track button clicks & swipes
                                        // from the same place
                                    },
                                    onSwipeCancel = {
                                        Log.d("Swipeable-Card", "Cancelled swipe")
                                        hint = "You canceled the swipe"
                                    }
                                ),
                            matchProfile = matchProfile
                        )
                    }

                    LaunchedEffect(matchProfile, state.swipedDirection) {
                        if (state.swipedDirection != null) {
                            hint = "You swiped ${stringFrom(state.swipedDirection!!)}"
                        }
                    }
                }
            }

            Row(
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 32.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                CircleButton(
                    onClick = {
                        scope.launch {
                            /**
                             * Finds the last card in the list with an offset of (0, 0) and swipes it left.
                             */
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second
                            last?.swipe(Direction.Left)
                        }
                    },
                    icon = Icons.Rounded.Close
                )

                CircleButton(
                    onClick = {
                        scope.launch {
                            /**
                             * Finds the last card in the list with an offset of (0, 0) and swipes it right.
                             */
                            val last = states.reversed()
                                .firstOrNull {
                                    it.second.offset.value == Offset(0f, 0f)
                                }?.second

                            last?.swipe(Direction.Right)
                        }
                    },
                    icon = Icons.Rounded.Favorite
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSwipeCards() {
    SwipeCards()
}

/**
 * Renders an icon button with a circle shape, primary color as background, and the given icon.
 *
 * @param onClick a lambda that is invoked when the button is clicked
 * @param icon the icon to display in the button
 */
@Composable
private fun CircleButton(
    onClick: () -> Unit,
    icon: ImageVector,
) {

    IconButton(
        modifier = Modifier
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.onPrimary)
            .size(56.dp)
            .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape),
        onClick = onClick
    ) {
        Icon(
            icon,
            null,
            tint = MaterialTheme.colorScheme.tertiary,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PrevCircleButton() {
    CircleButton(onClick = { /*TODO*/ }, icon = Icons.Rounded.Favorite)
}

/**
 * Renders a [Card] containing an [Image] of the [MatchProfile] and their name.
 *
 * @param modifier the [Modifier] to apply to the card
 * @param matchProfile the [MatchProfile] to render
 * Renders an [Image] of the [MatchProfile] using the provided [painterResource] and [ContentScale].
 *
 * @param contentScale the [ContentScale] to use when resizing the image
 * @param modifier the [Modifier] to apply to the image
 * @param painter the [painterResource] to use to draw the image
 * @param contentDescription an optional [String] used by accessibility services to provide an accessible name for the image
 *
 * Renders a [Text] of the [MatchProfile.name] using the provided [MaterialTheme.colorScheme.onPrimary],
 * [FontWeight.Medium], and [fontSize] properties.
 *                  *
 * @param text the [String] to render as text
 * @param color the [Color] to use when painting the text
 * @param fontSize the [Sp] size of the text
 * @param fontWeight the [FontWeight] to use when drawing the text
 * @param modifier the [Modifier] to apply to the text
 */
@Composable
private fun ProfileCard(
    modifier: Modifier,
    matchProfile: MatchProfile,
) {
    Card(modifier) {
        Box {
            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(matchProfile.drawableResId),
                contentDescription = stringResource(id = R.string.profile)
            )
            Scrim(Modifier.align(Alignment.BottomCenter))
            Column(Modifier.align(Alignment.BottomCenter)) {
                Text(
                    text = matchProfile.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}


/**
 * Renders a text with the given [text] and [textAlign] properties.
 *
 * @param text the text to display
 * @param color the color of the text
 * @param fontWeight the font weight of the text
 * @param fontSize the font size of the text
 * @param textAlign the alignment of the text
 * @param modifier the modifier to apply to the text
 */
@Composable
private fun Hint(text: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(horizontal = 24.dp, vertical = 32.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Creates a [DisposableEffect] that sets the system bars color to transparent.
 *
 * @param systemUiController the [rememberSystemUiController] to use
 * @param useDarkIcons whether to use dark icons in the system bars
 * Sets the system bars color to transparent using the provided [systemUiController].
 *
 * @param color the color to set the system bars to
 * @param darkIcons whether to use dark icons in the system bars
 * @param isNavigationBarContrastEnforced whether to enforce contrast in the navigation bar
 */
@Composable
private fun TransparentSystemBars() {

    val systemUiController = rememberSystemUiController()
    val useDarkIcons = false

    DisposableEffect(systemUiController, useDarkIcons) {

        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false,
        )
        // systemUiController.isStatusBarVisible = false // Status bar
        //systemUiController.isNavigationBarVisible = false // Navigation bar
        //systemUiController.isSystemBarsVisible = false // Status & Navigation bars
        //systemUiController.navigationBarDarkContentEnabled = false
        onDispose {}
    }
}

private fun stringFrom(direction: Direction): String {
    return when (direction) {
        Direction.Left -> "Left 👈"
        Direction.Right -> "Right 👉"
        Direction.Up -> "Up 👆"
        Direction.Down -> "Down 👇"
    }
}


@Composable
fun Scrim(modifier: Modifier = Modifier) {
    Box(
        modifier
            .background(Brush.verticalGradient(listOf(Color.Transparent, Color.Black)))
            .height(180.dp)
            .fillMaxWidth()
    )
}