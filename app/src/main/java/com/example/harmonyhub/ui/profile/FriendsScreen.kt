package com.example.harmonyhub.ui.profile

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.harmonyhub.R
import com.example.harmonyhub.domain.repository.FirebaseUser
import com.example.harmonyhub.presentation.viewmodel.FriendListFetchingState
import com.example.harmonyhub.presentation.viewmodel.FriendListViewModel
import com.example.harmonyhub.presentation.viewmodel.UserDataViewModel
import com.example.harmonyhub.ui.components.Friend
import com.example.harmonyhub.ui.components.FriendCard
import com.example.harmonyhub.ui.components.contains
import com.example.harmonyhub.ui.theme.NotoSans

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FriendsScreen(
    friends: List<Friend>,
    friendRequests: List<Friend>,
    onBackButtonClicked: () -> Unit,
    onAddButtonClicked: () -> Unit,
    onUnfriendClicked: () -> Unit,
    onWatchPlaylistClicked: () -> Unit,
    friendListViewModel: FriendListViewModel = hiltViewModel(),
    userDataViewModel: UserDataViewModel = hiltViewModel(),
) {

    var query by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var newFriendEmail by remember { mutableStateOf("") }
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    var isRequestsBottomSheetVisible by remember { mutableStateOf(false) }
    var selectedFriend by remember { mutableStateOf<Friend?>(null) }
    val focusManager = LocalFocusManager.current
    val searchResults = friends.filter { it.contains(query, ignoreCase = true) }

    val email = userDataViewModel.email.observeAsState()

    var showFriendRequestsDialog by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    val friendListFetchingState = friendListViewModel.dataFetchingState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(friendListFetchingState.value) {
        when (friendListFetchingState.value) {
            is FriendListFetchingState.Error -> {
                val message = (friendListFetchingState.value as FriendListFetchingState.Error).message
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                friendListViewModel.resetDataFetchingState()
            }
            is FriendListFetchingState.Success -> {
                when (val data = (friendListFetchingState.value as FriendListFetchingState.Success).data) {
                    is String -> {
                        val message = data as String
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        friendListViewModel.resetDataFetchingState()
                    }
                }
            }

            else -> {}
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Danh sách bạn bè",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.weight(1f))
            Row {
                IconButton(onClick = { showDialog = true }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        modifier = Modifier.size(24.dp)
                    )
                }
                BadgedBox(
                    badge = {
                        if (friendRequests.isNotEmpty()) {
                            Badge(
                                containerColor = Color(0xFF00FAF2)
                            ) {
                                Text(
                                    "${friendRequests.size}",
                                    fontFamily = NotoSans,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black
                                )
                            }
                        }
                    },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    IconButton(onClick = { showFriendRequestsDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Friend Requests",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
        SnackbarHost(hostState = snackbarHostState)

        Spacer(modifier = Modifier.height(16.dp))

        // Ô tìm kiếm
        TextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(
                    text = "Tìm kiếm bạn bè...",
                    style = TextStyle(fontFamily = NotoSans, fontSize = 16.sp)
                )
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(16.dp)),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(fontFamily = NotoSans, fontSize = 20.sp),
            colors = textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                containerColor = Color.Gray.copy(alpha = 0.2f)
            ),
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            },
        )

        // Thông tin số bạn bè
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${searchResults.size} bạn bè",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    color = Color.Gray
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Danh sách bạn bè
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(friends) { friend ->
                FriendCard(
                    friend = friend,
                    screenType = "Friends",
                    onMoreClick = {
                        isBottomSheetVisible = true
                        selectedFriend = friend
                    },
                    onAcceptClick = {},
                    onRejectClick = {}
                )
            }
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "Thêm bạn bè",
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                Column {
                    OutlinedTextField(
                        value = newFriendEmail,
                        onValueChange = { newFriendEmail = it },
                        label = { Text("Email bạn bè") },
                        singleLine = true,
                        maxLines = 1,
                        textStyle = TextStyle(fontFamily = NotoSans, fontSize = 16.sp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF00FAF2),
                            focusedLabelColor = Color(0xFF00FAF2),
                        ),
                        trailingIcon = {
                            // Hiển thị icon xóa nếu TextField có dữ liệu
                            if (newFriendEmail.isNotEmpty()) {
                                IconButton(onClick = { newFriendEmail = "" }) {
                                    Icon(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear"
                                    )
                                }
                            }
                        },
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                        if (newFriendEmail == email.value) {
                            Toast.makeText(context, "Bạn không thể gửi lời mời kết bạn đến chính mình", Toast.LENGTH_SHORT).show()
                        } else {
                            friendListViewModel.searchForEmail(newFriendEmail)
                        }
                    },
                    enabled = newFriendEmail.isNotBlank()
                ) {
                    Text(
                        "OK",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (newFriendEmail.isNotBlank()) Color(0xFF00FAF2) else Color.Gray
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text(
                        "Hủy",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FAF2)
                    )
                }
            }
        )
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetContent(
                onDismiss = { isBottomSheetVisible = false },
                selectedFriend = selectedFriend,
                onWatchPlaylistClicked = onUnfriendClicked,
                onUnFriendClicked = onUnfriendClicked
            )
        }
    }
    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = { isBottomSheetVisible = false },
            sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ) {
            BottomSheetContent(
                onDismiss = { isBottomSheetVisible = false },
                selectedFriend = selectedFriend,
                onWatchPlaylistClicked = onUnfriendClicked,
                onUnFriendClicked = onUnfriendClicked
            )
        }
    }
    if (showFriendRequestsDialog) {
        AlertDialog(
            onDismissRequest = { showFriendRequestsDialog = false },
            title = {
                Text(
                    text = "Yêu cầu kết bạn",
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            },
            text = {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(friendRequests) { friendRequest ->
                        FriendCard(
                            friend = friendRequest,
                            screenType = "Friend Requests",
                            onMoreClick = {
                                isRequestsBottomSheetVisible = true
                                selectedFriend = friendRequest
                            },
                            onAcceptClick = { /* Todo */ },
                            onRejectClick = { /* Todo */ }
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showFriendRequestsDialog = false }) {
                    Text(
                        text = "Đóng",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF00FAF2)
                    )
                }
            }
        )
    }

}

@Composable
private fun BottomSheetContent(
    onDismiss: () -> Unit,
    selectedFriend: Friend?,
    onWatchPlaylistClicked: () -> Unit,
    onUnFriendClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        selectedFriend?.let { friend ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(friend.imageResId),
                    contentDescription = "Friend Image",
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = friend.name,
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        maxLines = 1
                    )
                    Text(
                        text = friend.email,
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        color = Color.Gray,
                        maxLines = 1
                    )
                }
            }
            HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDismiss()
                        onWatchPlaylistClicked()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.queue_music),
                    contentDescription = "Watch Playlist",
                    tint = Color.LightGray,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Xem playlist",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = NotoSans, fontSize = 16.sp
                )

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onDismiss()
                        onUnFriendClicked()
                    }
            ) {
                Icon(
                    painter = painterResource(R.drawable.remove),
                    contentDescription = "Unfriend",
                    tint = Color.LightGray,
                    modifier = Modifier.size(25.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    "Hủy kết bạn",
                    modifier = Modifier.padding(vertical = 8.dp),
                    fontFamily = NotoSans, fontSize = 16.sp
                )

            }
        }
    }
}



