package com.raveline.ourrelationsapp.ui.screen.profileScreen.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.raveline.ourrelationsapp.R
import com.raveline.ourrelationsapp.ui.screen.profileScreen.selectedImageUri
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel
import java.util.Locale

@Composable
fun ProfileHeader(
    urlImage: String?,
    userName: String?,
    mSize: Dp = 200.dp,
) {

    val selectedImageResult: MutableState<Uri?> = remember {
        mutableStateOf(null)
    }

    val gradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            Color.LightGray,
            Color.Transparent,
            Color.LightGray
        ),
        start = Offset(160f, 140f),
        end = Offset(0f, 0f),
    )

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
    ) {
        selectedImageUri = it
        selectedImageResult.value = it
    }

    Column(
        modifier =
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(mSize)
                .border(
                    width = 6.dp,
                    shape = RoundedCornerShape(100.dp),
                    color = Color.Transparent,
                )
                .padding(4.dp)
                .drawWithContent {
                    // Draw the original content
                    drawContent()
                    // Draw the custom border
                    drawRoundRect(
                        brush = gradient,
                        size = Size(size.width - 6.dp.toPx(), size.height - 6.dp.toPx()),
                        topLeft = Offset(2.dp.toPx(), 2.dp.toPx()),
                        style = Stroke(width = 8.dp.toPx()),
                        cornerRadius = CornerRadius(100.dp.toPx(), 100.dp.toPx())
                    )
                }
                .clickable {
                    launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
        ) {
            AsyncImage(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(mSize - 12.dp) // Subtract border width to fit within the border
                    .clip(RoundedCornerShape(100.dp)),
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(selectedImageResult.value?:urlImage)
                    .crossfade(true)
                    .allowHardware(true)
                    .memoryCachePolicy(CachePolicy.ENABLED)
                    .error(R.drawable.ic_network_error)
                    .placeholder(R.drawable.ic_network)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = "Image Profile",
                alignment = Alignment.Center
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = userName?.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            }
                .toString(),
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily.SansSerif
            )
        )
    }

}


@Preview(showBackground = true)
@Composable
fun ProfileHeaderPreview() {
    ProfileHeader(
        urlImage = null,
        userName = "App name",
    )
}