package dev.itsu.manabe.ui.course

import android.content.Intent
import android.net.Uri
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import dev.itsu.manabe.R
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.api.model.CourseId
import dev.itsu.manabe.ifNotNull
import dev.itsu.manabe.ifNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CourseActivityPresenter(private val activity: CourseActivity) {

    private var course: Course? = null
    private var courseId: CourseId? = null

    suspend fun updateData(course: Course) {
        this.course = course

        withContext(Dispatchers.IO) {
            Manaba.getImage(course.imageUrl)
        }.let {
            activity.setIcon(it ?: ResourcesCompat.getDrawable(activity.resources, R.drawable.default_icon, activity.theme)!!.toBitmap())
        }

        withContext(Dispatchers.IO) {
            Manaba.getCourseId(course.url)
        }.let {
            this.courseId = it
            activity.setCourseId("#${it?.id}")
        }

        activity.setTitle(course.title)
        activity.setTeachers(course.teachers.joinToString(", "))
        activity.setAssignmentNotificationVisible(course.isReportAvailable || course.isTestOrQuestionAvailable)

        var seasons = ""
        course.seasons.forEach { key, value ->
            seasons += "、$key"
            value.forEach {
                seasons += "$it"
            }
        }
        seasons = seasons.substring(1)

        var times = ""
        course.times.forEach {key, value ->
            times += "、$key"
            value.forEach {
                times += "$it"
            }
        }
        times = times.substring(1)

        activity.setSeasons(course.year, seasons, times)
    }

    fun openSyllabus() {
        courseId ifNotNull {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://kdb.tsukuba.ac.jp/syllabi/2021/${courseId!!.id}/jpn")))
            it
        } ifNull {
            activity.showToast(R.string.loading)
        }
    }

    fun openInExplorer() {
        course ifNotNull {
            activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(course!!.url)))
            it
        } ifNull {
            activity.showToast(R.string.loading)
        }
    }

}