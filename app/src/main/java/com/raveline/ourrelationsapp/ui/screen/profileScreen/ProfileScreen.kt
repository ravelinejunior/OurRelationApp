package com.raveline.ourrelationsapp.ui.screen.profileScreen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.raveline.ourrelationsapp.ui.common.components.CommonDivider
import com.raveline.ourrelationsapp.ui.common.components.CommonProgress
import com.raveline.ourrelationsapp.ui.domain.models.GenderEnum
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.navigation.routes.navigateToIntroProfile
import com.raveline.ourrelationsapp.ui.screen.components.OurRelationsBottomAppBar
import com.raveline.ourrelationsapp.ui.screen.profileScreen.components.ProfileHeader
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel.Companion.mUser
import kotlinx.coroutines.launch

var selectedImageUri: Uri? = null

@Composable
fun ProfileScreen(
    navController: NavController,
    vm: AuthenticationViewModel,
    userData: UserDataModel?
) {
    val scope = rememberCoroutineScope()
    val g = if (userData?.gender.isNullOrEmpty()) GenderEnum.OTHER.name
    else userData!!.gender!!.uppercase()
    val gPref = if (userData?.genderPreference.isNullOrEmpty()) "FEMALE"
    else userData!!.genderPreference!!.uppercase()
    var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
    var userName by rememberSaveable { mutableStateOf(userData?.userName ?: "") }
    var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }
    var gender by rememberSaveable { mutableStateOf(GenderEnum.valueOf(g)) }
    var genderPreference by rememberSaveable { mutableStateOf(GenderEnum.valueOf(gPref)) }

    val scrollState = rememberScrollState()

    Column {
        Box {
            CommonProgress(viewModel = vm)
            ProfileContent(
                modifier = Modifier
                    .verticalScroll(scrollState)
                    .padding(8.dp),
                vm = vm,
                name = name,
                username = userName,
                bio = bio,
                gender = gender,
                genderPreference = genderPreference,
                onNameChange = { name = it },
                onUsernameChange = { userName = it },
                onBioChange = { bio = it },
                onGenderChange = { gender = it },
                onGenderPreferenceChange = { genderPreference = it },
                onSave = {
                    scope.launch {
                        selectedImageUri?.let {
                            vm.uploadProfileImage(uri = it, uid = userData?.userId.toString())
                        }
                        vm.createOrUpdateProfile(
                            name = name,
                            userName = userName,
                            bio = bio,
                            gender = gender,
                            genderPreference = genderPreference,
                            imageUrl = userData?.imageUrl
                        )

                        navController.navigateToIntroProfile(
                            navOptions = navOptions {
                                launchSingleTop = true
                            },
                            userData = mUser,
                        )
                    }
                },
                onBack = {
                    navController.navigateToIntroProfile(
                        navOptions {
                            launchSingleTop = true
                            launchSingleTop
                        },
                        userData = userData
                    )
                },
                userData = userData!!
            )
        }

        OurRelationsBottomAppBar(
            selectedItem = OurRelationsAppBarItem.ProfileItemBar.position,
            items = bottomAppBarItems,
            mItem = OurRelationsAppBarItem.ProfileItemBar
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier,
    vm: AuthenticationViewModel,
    name: String,
    username: String,
    bio: String,
    gender: GenderEnum,
    genderPreference: GenderEnum,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onGenderChange: (GenderEnum) -> Unit,
    onGenderPreferenceChange: (GenderEnum) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    userData: UserDataModel,
) {
    Column(modifier = modifier) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Back",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onBack.invoke() })
            Text(
                text = "Save",
                modifier = Modifier
                    .padding(8.dp)
                    .clickable { onSave.invoke() })
        }

        CommonDivider()

        ProfileImage(vm = vm)

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(100.dp))
            TextField(
                value = name,
                onValueChange = onNameChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                maxLines = 1,
                singleLine = true
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(100.dp))
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                singleLine = true,
                maxLines = 1
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Bio", modifier = Modifier.width(100.dp))
            TextField(
                value = bio,
                onValueChange = onBioChange,
                modifier = Modifier
                    .height(150.dp),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = MaterialTheme.colorScheme.onBackground,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colorScheme.onBackground,
                ),
                singleLine = false
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "I am a:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp)
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = gender == GenderEnum.MALE,
                        onClick = { onGenderChange(GenderEnum.MALE) })
                    Text(
                        text = "Man",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderChange(GenderEnum.MALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = gender == GenderEnum.FEMALE,
                        onClick = { onGenderChange(GenderEnum.FEMALE) })
                    Text(
                        text = "Woman",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderChange(GenderEnum.FEMALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = gender == GenderEnum.OTHER,
                        onClick = { onGenderChange(GenderEnum.OTHER) })
                    Text(
                        text = "Other",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderChange(GenderEnum.FEMALE) })
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.Top
        ) {
            Text(
                text = "Looking for:", modifier = Modifier
                    .width(100.dp)
                    .padding(8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == GenderEnum.MALE,
                        onClick = { onGenderPreferenceChange(GenderEnum.MALE) })
                    Text(
                        text = "Men",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(GenderEnum.MALE) },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == GenderEnum.FEMALE,
                        onClick = { onGenderPreferenceChange(GenderEnum.FEMALE) },
                    )
                    Text(
                        text = "Women",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(GenderEnum.FEMALE) },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == GenderEnum.OTHER,
                        onClick = { onGenderPreferenceChange(GenderEnum.OTHER) })
                    Text(
                        text = "Other",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(GenderEnum.OTHER) },
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { onSave.invoke() },
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Save",
                modifier = Modifier
                    .fillMaxWidth(),
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
fun ProfileImage(vm: AuthenticationViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->
        uri?.let {
            // Update the selected image URI in real-time
            selectedImageUri = it
            vm.uploadImageUI(it)
        }
    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        CommonProgress(viewModel = vm)
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                   // launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(200.dp)
            ) {
                //  CommonImage(data = imageUrl)
                ProfileHeader(urlImage = mUser?.imageUrl, userName = mUser?.name)
            }
            Text(text = "Change profile picture")
        }

    }
}
