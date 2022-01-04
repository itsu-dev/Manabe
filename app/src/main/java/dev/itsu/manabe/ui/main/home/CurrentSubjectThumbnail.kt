package dev.itsu.manabe.ui.main.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dev.itsu.manabe.R
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.ui.course.CourseActivity

class CurrentSubjectThumbnail(
    private val context: Context,
    private val icon: Bitmap,
    private val title: String,
    private val startAt: String,
    private val endAt: String,
    private val subjectId: String,
    private val url: String,
    private val course: Course
) {
    fun newInstance(): LinearLayout {
        val thumbnail = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.thumbnail_current_subject, null, false) as LinearLayout
        return thumbnail.also {
            it.findViewById<ImageView>(R.id.current_subject_icon_view).setImageBitmap(icon)
            it.findViewById<TextView>(R.id.current_subject_title_text).text = title
            it.findViewById<TextView>(R.id.current_subject_time_text).text = "$startAt~$endAt"
            it.findViewById<TextView>(R.id.current_subject_id_text).text = subjectId
            it.findViewById<LinearLayout>(R.id.current_subject_base).setOnClickListener {
                context.startActivity(Intent(context, CourseActivity::class.java).apply {
                    this.putExtra("course", course)
                })
                // context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))  // TODO
            }
        }
    }
}