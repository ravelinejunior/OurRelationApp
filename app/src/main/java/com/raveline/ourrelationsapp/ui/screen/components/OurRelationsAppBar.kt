package com.raveline.ourrelationsapp.ui.screen.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
        modifier = modifier
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

@Preview
@Composable
fun PreviewOurRelationsAppBar() {
    OurRelationsAppTheme {
        OurRelationsAppBar(mItem = bottomAppBarItems.first(), items = bottomAppBarItems)
    }
}