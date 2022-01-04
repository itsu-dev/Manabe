package dev.itsu.manabe.ui.main.home

import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import dev.itsu.manabe.R

class AssignmentThumbnail(
    private val context: Context,
    private val items: List<AssignmentThumbnailItem>
) {

    fun newInstance(): LinearLayout {
        val thumbnail = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.thumbnail_assignment, null, false) as LinearLayout
        thumbnail.findViewById<TextView>(R.id.thumbnail_assignment_subtitle_text).text = context.getString(R.string.home_assignments_description, items.size)
        thumbnail.findViewById<LinearLayout>(R.id.thumbnail_assignment_layout).also {
            var layout = LinearLayout(context)
            items.forEachIndexed { index, item ->
                if (index % 3 == 0) {
                    layout = LinearLayout(context).also {
                        it.dividerDrawable = ResourcesCompat.getDrawable(context.resources, R.drawable.empty_divider, context.theme)
                        it.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
                        it.orientation = LinearLayout.VERTICAL
                    }
                    it.addView(layout)
                }
                layout.addView(item.newInstance())
            }
        }
        return thumbnail
    }

}