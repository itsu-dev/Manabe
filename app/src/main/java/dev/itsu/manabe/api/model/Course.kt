package dev.itsu.manabe.api.model

import java.io.Serializable

data class Course(
    val title: String,
    val url: String,
    val imageUrl: String,
    val isNewsAvailable: Boolean,
    val isTestOrQuestionAvailable: Boolean,
    val isReportAvailable: Boolean,
    val isThreadAvailable: Boolean,
    val isIndividualAvailable: Boolean,
    val year: Int,
    val seasons: Map<String, List<String>>,  // {'春': ['A', 'B', 'C'], ...}
    val times: Map<String, List<Int>>, // {'月': [1, 2], ...}
    val information: String,
    val teachers: List<String>,
) : Serializable