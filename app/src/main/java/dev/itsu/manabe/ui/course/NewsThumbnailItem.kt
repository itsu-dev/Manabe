package dev.itsu.manabe.ui.course

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import dev.itsu.manabe.R
import dev.itsu.manabe.ui.web.WebActivity

class NewsThumbnailItem(
    private val context: Context,
    private val title: String,
    private val author: String,
    private val updatedAt: String,
    private val subject: String,
    private val url: String
) {
    fun newInstance(): LinearLayout {
        val thumbnail = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.thumbnail_news, null, false) as LinearLayout
        return thumbnail.also {
            it.findViewById<TextView>(R.id.thumbnail_news_title_text).text = title
            it.findViewById<TextView>(R.id.thumbnail_news_teacher_text).text = author
            it.findViewById<TextView>(R.id.thumbnail_news_date_text).text = updatedAt
            it.findViewById<TextView>(R.id.thumbnail_news_subject_text).text = subject
            it.setOnClickListener {
                context.startActivity(Intent(context, WebActivity::class.java).apply {
                    this.putExtra("url", url)
                    this.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }
        }
    }
}