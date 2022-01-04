package dev.itsu.manabe.ui.main.home

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import dev.itsu.manabe.R

class AssignmentThumbnailItem(
    private val context: Context,
    private val background: Drawable,
    private val icon: Bitmap,
    private val title: String,
    private val subjectName: String,
    private val date: String,
    private val type: String,
    private val url: String
) {
    fun newInstance(): LinearLayout {
        val thumbnail = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.thumbnail_assignment_item, null, false) as LinearLayout
        return thumbnail.also {
            it.findViewById<ImageView>(R.id.thumbnail_assignment_icon_view).setImageBitmap(icon)
            it.findViewById<TextView>(R.id.thumbnail_assignment_title_text).text = title
            it.findViewById<TextView>(R.id.thumbnail_assignment_subject_text).text = subjectName
            it.findViewById<TextView>(R.id.thumbnail_assignment_date_text).text = date
            it.findViewById<TextView>(R.id.thumbnail_assignment_type_text).text = type
            it.setOnClickListener {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
            it.findViewById<LinearLayout>(R.id.thumbnail_assignment_base_layout).also {
                it.background = background
            }
        }
    }
}