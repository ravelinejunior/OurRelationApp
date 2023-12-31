package com.raveline.ourrelationsapp.ui.common.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.viewmodel.OurRelationsViewModel

@Composable
fun CommonProgressSpinner() {
    Row(
        modifier = Modifier
            .alpha(0.5f)
            .background(Color.LightGray)
            .clickable(enabled = false) {}
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(100.dp),
            color = MaterialTheme.colorScheme.tertiary,
            strokeWidth = 6.dp
        )
    }
}

@Composable
fun NotificationMessage(viewModel: OurRelationsViewModel) {
    val notificationState = viewModel.popUpNotification.value
    val notifyMessage = notificationState?.getContentOrNull()
    if (!notifyMessage.isNullOrEmpty()) {
        Toast.makeText(LocalContext.current, notifyMessage, Toast.LENGTH_SHORT).show()
    }
}