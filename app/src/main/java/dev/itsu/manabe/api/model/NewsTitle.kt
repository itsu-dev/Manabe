package dev.itsu.manabe.api.model

data class NewsTitle(
    val title: String,
    val newsUrl: String,
    val author: String,
    val updatedAt: Long
)