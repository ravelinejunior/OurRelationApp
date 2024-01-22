package com.raveline.ourrelationsapp.ui.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.theme.OurRelationsAppTheme


@Composable
fun OurRelationsAppBar(
    mItem: OurRelationsAppBarItem,
    modifier: Modifier = Modifier,
    items: List<OurRelationsAppBarItem> = emptyList(),
    onItemChanged: (OurRelationsAppBarItem) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 12.dp
    ) {
        items.forEach { item ->
            val label = item.label
            val icon = item.icon
            NavigationBarItem(
                label = {
                    Text(label)
                },
                selected = mItem.label == label,
                icon = {
                    Icon(imageVector = icon, contentDescription = label)
                },
                onClick = {
                    onItemChanged(item)
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewOurRelationsAppBar() {
    OurRelationsAppTheme {
        OurRelationsAppBar(mItem = bottomAppBarItems.first(), items = bottomAppBarItems)
    }
}