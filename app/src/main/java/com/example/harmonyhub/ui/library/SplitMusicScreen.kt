package com.example.harmonyhub.ui.library

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.enableLiveLiterals
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.example.harmonyhub.data.SongRepository
import com.example.harmonyhub.ui.components.Song
import com.example.harmonyhub.ui.components.SongCard
import com.example.harmonyhub.ui.theme.NotoSans
import java.io.File

@Composable
fun SplitMusicScreen(
    url1: String?,
    url2: String?,
    onBackButtonClicked: () -> Unit,
) {
    val exoPlayers = mutableListOf<ExoPlayer>()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    // Chuyển đổi URL thành đối tượng Song
    val song1 = url1?.let { findSongByUrl(it) }
    val song2 = url2?.let { findSongByUrl(it) }
    DisposableEffect(Unit) {
        onDispose {
            // Release all players in the list when the screen is disposed
            exoPlayers.forEach { player ->
                player.release()
            }
        }
    }
    song1!!.let {
        copyMp3ToInternalStorage(context,"bass_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"drum_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"vocal_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"other_${it.id}.mp3")
    }
    song2!!.let {
        copyMp3ToInternalStorage(context,"bass_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"drum_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"vocal_${it.id}.mp3")
        copyMp3ToInternalStorage(context,"other_${it.id}.mp3")
    }
    // Dùng một map để giữ trạng thái của từng checkbox cho mỗi bài hát
    val checkedStatesSong1 = remember { mutableStateOf(mapOf<String, Boolean>()) }
    val checkedStatesSong2 = remember { mutableStateOf(mapOf<String, Boolean>()) }

    // Quản lý trạng thái của nút (Phát/Dừng)
    val isPlaying = remember { mutableStateOf(false) }

    val listCheckBoxSelected : MutableList<String> = mutableListOf()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Thanh tiêu đề
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onBackButtonClicked() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                text = "Bản tách nhạc",
                style = TextStyle(
                    fontFamily = NotoSans,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            item {
                Text(
                    text = "Bài hát gốc",
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            items(listOf(song1, song2)) { song ->
                if (song != null) {
                    SongCard(
                        song = song,
                        more = null,
                        onSongClick = {},
                        onMoreClick = {}
                    )
                }
            }

            item {
                Text(
                    text = "Các bản tách",
                    fontFamily = NotoSans,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            // Duyệt qua danh sách các phần cho từng bài hát (Bass, Drum, Beat, Vocal)
            items(
                listOf(
                    Pair(song1, checkedStatesSong1),
                    Pair(song2, checkedStatesSong2)
                )
            ) { (song, checkedStates) ->
                // Duyệt qua các phần của bài hát
                val parts = listOf("Bass", "Drum", "Beat", "Vocal")
                parts.forEach { part ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hiển thị tên phần (Bass, Drum, Beat, Vocal)
                        Text(
                            text = "$part ${if (song == song1) song1!!.name else song2!!.name}",
                            fontFamily = NotoSans,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f),
                            color = Color.White
                        )

                        // Checkbox ở cuối mỗi phần (Chỉ thay đổi khi isPlaying là false)
                        val isChecked = checkedStates.value[part] ?: false
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { isChecked ->
                                // Cập nhật trạng thái checkbox khi người dùng thay đổi (Chỉ khi isPlaying là false)
                                if (!isPlaying.value) {
                                    checkedStates.value = checkedStates.value.toMutableMap().apply {
                                        put(part, isChecked)
                                    }
                                }
                            },
                            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF00FAF2), uncheckedColor = Color.Gray)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Kiểm tra trạng thái checkbox để quyết định màu nút
        val isAnyChecked =
            (checkedStatesSong1.value.values.any { it }) || (checkedStatesSong2.value.values.any { it })
        var count by remember { mutableStateOf(0)}


        Button(
            onClick = {

                val parts = listOf("Bass", "Drum", "Beat", "Vocal")
                parts.forEach{
                    part ->
                    if (checkedStatesSong1.value[part] == true) {
                        if (part == "Bass") {
                            listCheckBoxSelected.add("bass_${song1.id}.mp3")
                        } else if (part == "Drum") {
                            listCheckBoxSelected.add("drum_${song1.id}.mp3")
                        } else if (part == "Beat") {
                            listCheckBoxSelected.add("other_${song1.id}.mp3")
                        } else if (part == "Vocal") {
                            listCheckBoxSelected.add("vocal_${song1.id}.mp3")
                        }
                    }
                    if (checkedStatesSong2.value[part] == true) {
                        if (part == "Bass") {
                            listCheckBoxSelected.add("bass_${song2.id}.mp3")
                        } else if (part == "Drum") {
                            listCheckBoxSelected.add("drum_${song2.id}.mp3")
                        } else if (part == "Beat") {
                            listCheckBoxSelected.add("other_${song2.id}.mp3")
                        } else if (part == "Vocal") {
                            listCheckBoxSelected.add("vocal_${song2.id}.mp3")
                        }
                    }
                }
//                if (count == 0) {
//                    playMusicSplit(context, exoPlayers, listCheckBoxSelected)
//                    count = count + 1
//                    Log.d ("playsplitmusic", "playfrist")
//                }

                if (isPlaying.value == false) {
                    playMusicSplit(context, exoPlayers, listCheckBoxSelected)

                } else {
                   exoPlayers.forEach{
                       exoPlayer->
                        if (exoPlayer.isPlaying) {
                            exoPlayer.stop()
                            exoPlayer.release()
                        }
                    }
                }
                // Thực hiện hành động khi nhấn nút
                isPlaying.value = !isPlaying.value
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isAnyChecked) Color(0xFF00FAF2) else Color.Gray,
                contentColor = Color.DarkGray
            ),
            enabled = isAnyChecked, // Disable button nếu không có checkbox nào được chọn
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = if (isPlaying.value) "Dừng" else "Phát",
                fontFamily = NotoSans,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
    Runtime.getRuntime().addShutdownHook(Thread {
        exoPlayers.forEach { it.release() }
    })
}
fun findSongByUrl(url: String): Song? {
    return SongRepository.allSongs.find { it.url == url }
}


fun copyMp3ToInternalStorage(context: Context, fileName: String){
    val inputStream = context.assets.open(fileName) // Lấy file từ assets
    val outputFile = File(context.filesDir, fileName) // Đặt file ở filesDir
    if (!outputFile.exists()) {
        inputStream.use { input ->
            outputFile.outputStream().use { output ->
                input.copyTo(output) // Copy dữ liệu từ assets sang filesDir
            }
        }
    }
    //return outputFile
}
fun playMusicSplit(
    context: Context, exoPlayers: MutableList<ExoPlayer>
    , listCheckboxSelected : MutableList<String>
) {
    //val exoPlayers = mutableListOf<ExoPlayer>()
    val selectedFiles : MutableList<File> = mutableListOf()

    Log.d("playsplitmusic", "${listCheckboxSelected}")
    listCheckboxSelected.forEach{
        item->
        selectedFiles.add(File(context.filesDir, item))
    }


    for (file in selectedFiles) {
        if (file.exists()) { // Kiểm tra file có tồn tại không
            val player = ExoPlayer.Builder(context).build()

            // Tạo MediaItem từ file
            val mediaItem = MediaItem.fromUri(Uri.fromFile(file))
            player.setMediaItem(mediaItem)

            // Thêm ExoPlayer vào danh sách
            exoPlayers.add(player)
        } else {
            Log.e("ExoPlayer", "File không tồn tại: ${file.name}")
        }
    }
    for (player in exoPlayers) {
        player.prepare()
        player.play()
    }
    // Đảm bảo giải phóng tài nguyên khi không sử dụng
    Runtime.getRuntime().addShutdownHook(Thread {
        exoPlayers.forEach { it.release() }
    })
}

@Preview
@Composable
fun SplitMusicScreenPreview(){
    SplitMusicScreen("", "") { }
}

