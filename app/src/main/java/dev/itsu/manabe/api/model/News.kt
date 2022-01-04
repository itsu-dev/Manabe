package dev.itsu.manabe.api.model

data class News(
    val title: String,
    val author: String,
    val textHtml: String,
    val updatedAt: Long
)