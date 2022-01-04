package dev.itsu.manabe.ui.main.courses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import dev.itsu.manabe.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CoursesFragment : Fragment() {


    private lateinit var coursesFragment: View
    private val presenter = CoursesFragmentPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        coursesFragment = inflater.inflate(R.layout.fragment_courses, container, false)

        GlobalScope.launch(Dispatchers.Main) {
            presenter.updateCourses()
        }

        return coursesFragment
    }

    fun clearList() {
        coursesFragment.findViewById<LinearLayout>(R.id.courses_list).removeAllViews()
    }

    fun addCourseThumbnails(courses: List<CourseThumbnail>) {
        clearList()
        val layout = coursesFragment.findViewById<LinearLayout>(R.id.courses_list)
        courses.forEach {
            layout.addView(it.newInstance())
        }
    }

}