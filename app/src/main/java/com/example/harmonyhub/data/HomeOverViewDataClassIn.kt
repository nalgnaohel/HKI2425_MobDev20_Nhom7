package com.example.harmonyhub.data

data class Avatar(
    val url: String,
    val width: Int,
    val height: Int
)

data class Avatars(
    val avatar: List<Avatar>
)

data class Album(
    val type: String,
    val id: String,
    val name: String,
    val cover: List<Avatar>,
    val artists: List<Artist>
)

data class AlbumOut(
    val id: String,
    val name: String,
    val image: String,
    val listArtist: List<String>,
)

data class Artist(
    val type: String,
    val id: String,
    val name: String,
    val visuals: Avatars? = null
)

data class ArtistOut(
    val id: String,
    val name: String,
    val image: String?
)




data class PopularItem(
   val type: String,
   val id: String,
   val name: String,
   val cover: List<Avatar>,//album
   val artists: List<Artist>,//album
   val visuals: Avatars? = null,//Artist


)

data class Contents(
    val totalCount: Int,
    val items: List<PopularItem>
)

data class SectionItem(
    val type: String,
    val id: String,
    val title: String,
    val contents: Contents
)

data class Sections(
    val totalCount: Int,
    val items: List<SectionItem>
)

data class Response(
    val status: Boolean,
    val errorId: String,
    val sections: Sections
)

