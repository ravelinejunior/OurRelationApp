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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.raveline.ourrelationsapp.ui.common.components.CommonDivider
import com.raveline.ourrelationsapp.ui.common.components.CommonProgressSpinner
import com.raveline.ourrelationsapp.ui.domain.models.GenderEnum
import com.raveline.ourrelationsapp.ui.domain.models.UserDataModel
import com.raveline.ourrelationsapp.ui.navigation.routes.OurRelationsAppBarItem
import com.raveline.ourrelationsapp.ui.navigation.routes.bottomAppBarItems
import com.raveline.ourrelationsapp.ui.screen.components.OurRelationsAppBar
import com.raveline.ourrelationsapp.ui.viewmodel.AuthenticationViewModel

@Composable
fun ProfileScreen(
    vm: AuthenticationViewModel,
    userData: UserDataModel?
) {
    val g = if (userData?.gender.isNullOrEmpty()) GenderEnum.OTHER.name
    else userData!!.gender!!.uppercase()
    val gPref = if (userData?.genderPreference.isNullOrEmpty()) "FEMALE"
    else userData!!.genderPreference!!.uppercase()
    var name by rememberSaveable { mutableStateOf(userData?.name ?: "") }
    var username by rememberSaveable { mutableStateOf(userData?.userName ?: "") }
    var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "") }
    var gender by rememberSaveable { mutableStateOf(GenderEnum.valueOf(g)) }
    var genderPreference by rememberSaveable { mutableStateOf(GenderEnum.valueOf(gPref)) }

    val scrollState = rememberScrollState()

    Column {
        ProfileContent(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
                .padding(8.dp),
            vm = vm,
            name = name,
            username = username,
            bio = bio,
            gender = gender,
            genderPreference = genderPreference,
            onNameChange = { name = it },
            onUsernameChange = { username = it },
            onBioChange = { bio = it },
            onGenderChange = { gender = it },
            onGenderPreferenceChange = { genderPreference = it },
            onSave = {
            },
            onBack = {
            },
            onLogout = {

            },
            userData = UserDataModel()
        )

        OurRelationsAppBar(
            selectedItem = OurRelationsAppBarItem.ProfileItemBar.position,
            items = bottomAppBarItems,
            mItem = OurRelationsAppBarItem.ProfileItemBar
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
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
    onLogout: () -> Unit,
    userData: UserDataModel,
) {
    val imageUrl = userData.imageUrl

    Column(modifier = modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() })
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() })
        }

        CommonDivider()

        ProfileImage(imageUrl = imageUrl, vm = vm)

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
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                ),
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
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
                ),
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
                    focusedTextColor = Color.Black,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedLabelColor = Color.Black,
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
                    .padding(8.dp)
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
                            .clickable { onGenderPreferenceChange(GenderEnum.MALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == GenderEnum.FEMALE,
                        onClick = { onGenderPreferenceChange(GenderEnum.FEMALE) })
                    Text(
                        text = "Women",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(GenderEnum.FEMALE) })
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(
                        selected = genderPreference == GenderEnum.OTHER,
                        onClick = { onGenderPreferenceChange(GenderEnum.OTHER) })
                    Text(
                        text = "Any",
                        modifier = Modifier
                            .padding(4.dp)
                            .clickable { onGenderPreferenceChange(GenderEnum.OTHER) })
                }
            }
        }

        CommonDivider()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() })
        }

    }
}

@Composable
fun ProfileImage(imageUrl: String?, vm: AuthenticationViewModel) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
    ) { uri: Uri? ->

    }

    Box(modifier = Modifier.height(IntrinsicSize.Min)) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .clickable {
                    launcher.launch("image/*")
                },
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                //  CommonImage(data = imageUrl)
            }
            Text(text = "Change profile picture")
        }

        val isLoading = vm.inProgress.value
        if (isLoading)
            CommonProgressSpinner()
    }
}
