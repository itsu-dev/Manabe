package dev.itsu.manabe.ui.main.courses

import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.utils.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoursesFragmentPresenter(private val coursesFragment: CoursesFragment) {

    suspend fun updateCourses() = withContext(Dispatchers.IO) {
        if (!Manaba.loggedIn) Manaba.login(Preferences.getCredentials().first, Preferences.getCredentials().second)
        Manaba.getAllCourses()

    }.let {
        val result = mutableListOf<CourseThumbnail>()
        it.forEach {
            result.add(
                CourseThumbnail(
                    coursesFragment.requireContext(),
                    it
                )
            )
        }

        coursesFragment.addCourseThumbnails(result)
    }

}