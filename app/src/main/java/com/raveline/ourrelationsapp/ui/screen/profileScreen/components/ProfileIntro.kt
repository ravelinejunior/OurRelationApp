package com.raveline.ourrelationsapp.ui.screen.profileScreen.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.common.components.CommonSpacer
import com.raveline.ourrelationsapp.ui.common.components.StyledRow
import com.raveline.ourrelationsapp.ui.common.utils.customCapitalize
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel

@Composable
fun ProfileIntro(
    userDataModel: UserDataModel?,
    onSignOut: () -> Unit,
    navigateToEditProfile: (UserDataModel) -> Unit,
) {

    ProfileIntroComponent(
        userDataModel = userDataModel,
        onSignOut = onSignOut,
        navigateToEditProfile = {
            navigateToEditProfile(it)
        }
    )
}

@Composable
fun ProfileIntroComponent(
    userDataModel: UserDataModel?,
    onSignOut: () -> Unit,
    navigateToEditProfile: (UserDataModel) -> Unit
) {

    Surface(
        modifier =
        Modifier
            .fillMaxSize()
            .background(
                color = MaterialTheme.colorScheme.background
            )
            .padding(16.dp)
            .statusBarsPadding(),
    ) {
        Column(
            modifier =
            Modifier.verticalScroll(
                rememberScrollState()
            )
        ) {
            ProfileHeader(
                urlImage = userDataModel?.imageUrl,
                userName = customCapitalize(
                    userDataModel?.userName.toString()
                ),
            )

            CommonSpacer()

            StyledRow(
                icon = Icons.Rounded.Edit,
                label = "Edit Profile",
                color = MaterialTheme.colorScheme.primary,
                userDataModel = userDataModel,
                onSelectUser = {
                    navigateToEditProfile(userDataModel!!)
                }
            )
            StyledRow(icon = Icons.Rounded.Key, label = "Change Password")
            StyledRow(icon = Icons.Rounded.Info, label = "Information")

            CommonSpacer(value = 32.dp)

            StyledRow(
                icon = Icons.Rounded.Logout,
                label = "Logout",
                onSelect = onSignOut,
            )
        }
    }
}

@Preview(
    showSystemUi = true, showBackground = true, backgroundColor = 0xFFFFFFFF,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun ProfileIntroComponentPreview() {
    ProfileIntroComponent(
        userDataModel = UserDataModel(
            name = "App Name"
        ),
        onSignOut = {

        }
    ) {

    }
}






