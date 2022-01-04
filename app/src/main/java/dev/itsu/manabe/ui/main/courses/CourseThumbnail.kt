package dev.itsu.manabe.ui.main.courses

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dev.itsu.manabe.R
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.api.model.CourseId
import dev.itsu.manabe.ui.course.CourseActivity
import kotlinx.coroutines.*

class CourseThumbnail(
    private val context: Context,
    private val course: Course
) {

    fun newInstance(): LinearLayout {
        val thumbnail =
            (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.thumbnail_subject, null, false) as LinearLayout
        return thumbnail.also {
            GlobalScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    Pair(Manaba.getImage(course.imageUrl), Manaba.getCourseId(course.url))
                }.let { result ->
                    it.findViewById<ImageView>(R.id.subject_icon_view).setImageBitmap(result.first!!)
                    it.findViewById<TextView>(R.id.subject_id_text).text = "#" + (result.second as CourseId).id
                }
            }
            it.findViewById<TextView>(R.id.subject_title_text).text = course.title
            it.findViewById<TextView>(R.id.subject_teachers_text).text = course.teachers.joinToString(", ")
            it.findViewById<LinearLayout>(R.id.subject_base).setOnClickListener {
                context.startActivity(Intent(context, CourseActivity::class.java).apply {
                    this.putExtra("course", course)
                })
                // context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))  // TODO
            }

            it.findViewById<ImageView>(R.id.subject_icon_news).visibility = if (course.isNewsAvailable) View.VISIBLE else View.GONE
            it.findViewById<ImageView>(R.id.subject_icon_assignment).visibility = if (course.isTestOrQuestionAvailable) View.VISIBLE else View.GONE
            it.findViewById<ImageView>(R.id.subject_icon_report).visibility = if (course.isReportAvailable) View.VISIBLE else View.GONE
            it.findViewById<ImageView>(R.id.subject_icon_thread).visibility = if (course.isThreadAvailable) View.VISIBLE else View.GONE
            it.findViewById<ImageView>(R.id.subject_icon_indivisual).visibility = if (course.isIndividualAvailable) View.VISIBLE else View.GONE
        }
    }
}