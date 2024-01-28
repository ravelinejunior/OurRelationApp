package com.raveline.ourrelationsapp.ui.common.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.common.utils.customCapitalize
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel

@Composable
fun StyledRow(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    color: Color = MaterialTheme.colorScheme.primary,
    label: String,
    userDataModel: UserDataModel?=null,
    onSelect: () -> Unit = {},
    onSelectUser: (UserDataModel) -> Unit = {},
) {
    Box(
        modifier = modifier
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .clickable {
                if (userDataModel != null){
                    onSelectUser(userDataModel)
                }else{
                    onSelect()
                }
            }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.LightGray.copy(alpha = 0.1f))
                .padding(16.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BoxWithConstraints(
                modifier =
                Modifier
                    .size(32.dp)
                    .background(
                        color = color,
                        shape = RoundedCornerShape(100.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Icon Styled Row",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = customCapitalize(label),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StyledRowPrev() {
    StyledRow(
        color = MaterialTheme.colorScheme.primary,
        icon = Icons.Rounded.Search,
        label = "Search"
    )
}