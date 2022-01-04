package dev.itsu.manabe.ui.course

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import dev.itsu.manabe.MyApplication
import dev.itsu.manabe.R
import dev.itsu.manabe.api.model.Course
import java.lang.IllegalArgumentException

class CoursePagerAdapter(private val fragmentManager: FragmentManager, private val course: Course) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        const val PAGE_COUNT = 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0, 1, 2, 3 -> NewsListFragment.newInstance(course)
            // TODO
            else -> throw IllegalStateException()
        }
    }

    override fun getCount(): Int {
        return PAGE_COUNT
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> MyApplication.getInstance().getString(R.string.course_tab_news)
            1 -> MyApplication.getInstance().getString(R.string.course_tab_assignments)
            2 -> MyApplication.getInstance().getString(R.string.course_tab_contents)
            3 -> MyApplication.getInstance().getString(R.string.course_tab_threads)
            else -> throw IllegalArgumentException()
        }
    }
}