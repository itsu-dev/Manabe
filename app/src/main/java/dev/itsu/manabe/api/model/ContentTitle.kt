package dev.itsu.manabe.api.model

data class ContentTitle(
    val title: String,
    val contentUrl: String,
    val pageCount: Int,
    val updatedAt: Long
)