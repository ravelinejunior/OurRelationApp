package com.raveline.ourrelationsapp.ui.screen.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.navigation.routes.swipeNavigationRoute
import com.raveline.ourrelationsapp.ui.theme.OurRelationsAppTheme


@Composable
fun OurRelationsBottomAppBar(
    mItem: OurRelationsAppBarItem,
    modifier: Modifier = Modifier,
    selectedItem: Int,
    items: List<OurRelationsAppBarItem> = emptyList(),
    onItemChanged: (OurRelationsAppBarItem) -> Unit = {}
) {
    NavigationBar(
        modifier = modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 12.dp
    ) {

        items.forEachIndexed { index, item ->
            val label = item.label
            val icon = item.icon
            NavigationBarItem(
                selected = selectedItem == index,
                icon = {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (mItem.label == label && item.destination == swipeNavigationRoute) Icon(
                            imageVector = Icons.Rounded.Favorite,
                            tint = MaterialTheme.colorScheme.primary,
                            contentDescription = label
                        ) else Icon(imageVector = icon, contentDescription = label)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = item.label, style = MaterialTheme.typography.titleSmall)
                    }
                },
                onClick = {
                    onItemChanged(item)
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background,
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewOurRelationsAppBar() {
    OurRelationsAppTheme {
        OurRelationsBottomAppBar(
            mItem = bottomAppBarItems[1],
            items = bottomAppBarItems,
            selectedItem = 1,
        )
    }
}