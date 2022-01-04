package dev.itsu.manabe.ui.main.home

import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import dev.itsu.manabe.R
import dev.itsu.manabe.Utils
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.ifNotNull
import dev.itsu.manabe.utils.Preferences
import kotlinx.coroutines.*
import java.text.SimpleDateFormat

class HomeFragmentPresenter(private val homeFragment: HomeFragment) {

    suspend fun updateAssignments() = withContext(Dispatchers.IO) {
        if (!Manaba.loggedIn) Manaba.login(Preferences.getCredentials().first, Preferences.getCredentials().second)
        Manaba.getAssignments()

    }.let {
        val result = mutableListOf<AssignmentThumbnailItem>()
        it.forEach {
            result.add(
                AssignmentThumbnailItem(
                    homeFragment.requireContext(),
                    Utils.limitToColorDrawable(homeFragment.requireContext(), it.expiredAt),
                    ResourcesCompat.getDrawable(homeFragment.requireContext().resources, R.drawable.default_icon, homeFragment.requireContext().theme)!!.toBitmap(),
                    it.title,
                    it.course,
                    SimpleDateFormat("yyyy/MM/dd HH:mm").format(it.expiredAt),
                    it.type,
                    it.url
                )
            )
        }

        homeFragment.addAssignmentsThumbnail(AssignmentThumbnail(homeFragment.requireContext(), result))
    }

    suspend fun updateCurrentCourse() {
        val courses = withContext(Dispatchers.IO) {
            if (!Manaba.loggedIn) Manaba.login(Preferences.getCredentials().first, Preferences.getCredentials().second)
            Manaba.getAllCourses()
        }

        courses.forEach { course ->
            Utils.isHoldingClass(course.seasons, course.times) ifNotNull { pair ->
                GlobalScope.launch(Dispatchers.Main) {
                    updateCurrentCourse2(course, pair!!)
                }
                pair
            }
        }
    }

    private suspend fun updateCurrentCourse2(course: Course, pair: Pair<String, Int>) = withContext(Dispatchers.IO) {
        Pair(Manaba.getImage(course.imageUrl), Manaba.getCourseId(course.url))

    }.let {
        homeFragment.addCurrentSubjectThumbnail(
            CurrentSubjectThumbnail(
                homeFragment.requireContext(),
                it.first ?: ResourcesCompat.getDrawable(homeFragment.requireContext().resources, R.drawable.default_icon, homeFragment.requireContext().theme)!!.toBitmap(),
                course.title,
                Utils.TIMETABLES[pair.second]!![0],
                Utils.TIMETABLES[pair.second]!![1],
                "#${it.second?.id}",
                course.url,
                course
            )
        )
    }


}