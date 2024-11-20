package com.example.harmonyhub.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.harmonyhub.R
import com.example.harmonyhub.ui.theme.NotoSans

private val gradientBackground = Brush.verticalGradient(
    colors = listOf(Color(0xFF04A8A3), Color(0xFF0A91BD))
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onBackButtonClicked: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val username = remember { mutableStateOf("Thomas") }
    val profileImage = remember { mutableIntStateOf(R.drawable.hip) }

    val isNameDialogOpen = remember { mutableStateOf(false) }
    val isImageDialogOpen = remember { mutableStateOf(false) }

    if (isNameDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isNameDialogOpen.value = false },
            title = {
                Text(
                    text = "Đổi tên",
                    style = TextStyle(
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                )
            },
            text = {
                TextField(
                    value = username.value,
                    onValueChange = { newName -> username.value = newName },
                    placeholder = { Text("Tên mới") },
                    singleLine = true,
                    maxLines = 1,
                    textStyle = TextStyle(
                        fontFamily = NotoSans,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    colors = textFieldColors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        containerColor = Color.Gray.copy(alpha = 0.2f)
                    ),
                )
            },

            confirmButton = {
                Button(
                    onClick = { isNameDialogOpen.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .background(gradientBackground, shape = RoundedCornerShape(32.dp)),

                    ) {
                    Text(
                        text = "Lưu",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { isNameDialogOpen.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .background(gradientBackground, shape = RoundedCornerShape(32.dp)),
                ) {
                    Text(
                        text = "Hủy",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        )
    }

    if (isImageDialogOpen.value) {
        AlertDialog(
            onDismissRequest = { isImageDialogOpen.value = false },
            title = { Text("Thay đổi ảnh đại diện") },
            text = {
                Text("Chọn ảnh từ thư viện hoặc máy của bạn.")
            },
            confirmButton = {
                Button(
                    onClick = { isImageDialogOpen.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .background(gradientBackground, shape = RoundedCornerShape(32.dp)),
                ) {
                    Text(
                        text = "Lưu ảnh mới",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { isImageDialogOpen.value = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .height(40.dp)
                        .background(gradientBackground, shape = RoundedCornerShape(32.dp)),
                ) {
                    Text(
                        text = "Hủy",
                        fontFamily = NotoSans,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    isNameDialogOpen.value = false
                    isImageDialogOpen.value = false
                    focusManager.clearFocus()
                })
            }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Thanh tiêu đề
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
                text = "Thông tin cá nhân",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }
        Spacer(modifier = Modifier.height(32.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Khi nhấn vào ảnh, mở hộp thoại thay đổi ảnh
                Image(
                    painter = painterResource(id = profileImage.intValue),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .pointerInput(Unit) {
                            detectTapGestures(onTap = {
                                isImageDialogOpen.value = true
                            })
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                // Khi nhấn vào tên, mở hộp thoại thay đổi tên
                Text(
                    text = username.value,
                    style = TextStyle(
                        fontFamily = NotoSans,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White
                    ),
                    modifier = Modifier.pointerInput(Unit) {
                        detectTapGestures(onTap = {
                            isNameDialogOpen.value = true
                        })
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = "Email",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "thomas.todd@gmail.com",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Người theo dõi",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "3",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Đang theo dõi",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = Color.White
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "2",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            )
            Spacer(modifier = Modifier.height(16.dp))

        }

    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        onBackButtonClicked = {},
    )
}
