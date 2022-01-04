package dev.itsu.manabe.ui.course

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import dev.itsu.manabe.R
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.ifNotNull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CourseActivity : AppCompatActivity() {

    private val presenter = CourseActivityPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course)

        this.intent ifNotNull {
            val course = this.intent.getSerializableExtra("course") as Course
            GlobalScope.launch(Dispatchers.Main) {
                presenter.updateData(course)
            }

            this.findViewById<ViewPager>(R.id.course_view_pager).apply {
                this.adapter = CoursePagerAdapter(supportFragmentManager, course)
                this@CourseActivity.findViewById<TabLayout>(R.id.course_tab).setupWithViewPager(this)
            }

            it
        }

        this.findViewById<ImageButton>(R.id.course_open_in_explorer_button).setOnClickListener {
            presenter.openInExplorer()
        }

        this.findViewById<ImageButton>(R.id.course_open_syllabus_button).setOnClickListener {
            presenter.openSyllabus()
        }
    }

    fun showToast(id: Int) {
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }

    fun setIcon(icon: Bitmap) {
        this.findViewById<ImageView>(R.id.course_icon_image).setImageBitmap(icon)
    }

    fun setTitle(title: String) {
        this.findViewById<TextView>(R.id.course_title_text).text = title
    }

    fun setCourseId(id: String) {
        this.findViewById<TextView>(R.id.course_id_text).text = id
    }

    fun setSeasons(year: Int, seasons: String, times: String) {
        this.findViewById<TextView>(R.id.course_season_text).text = this.getString(R.string.course_season, year, seasons, times)
    }

    fun setTeachers(teachers: String) {
        this.findViewById<TextView>(R.id.course_teachers_text).text = this.getString(R.string.course_teachers, teachers)
    }

    fun setAssignmentNotificationVisible(visibility: Boolean) {
        this.findViewById<TextView>(R.id.course_assignment_text).visibility =
            if (visibility) TextView.VISIBLE else TextView.INVISIBLE
    }

}