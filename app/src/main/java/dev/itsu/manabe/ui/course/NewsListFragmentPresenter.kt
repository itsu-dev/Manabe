package dev.itsu.manabe.ui.course

import dev.itsu.manabe.MyApplication
import dev.itsu.manabe.api.Manaba
import dev.itsu.manabe.api.model.Course
import dev.itsu.manabe.utils.Preferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat

class NewsListFragmentPresenter {

    suspend fun update(newsListFragment: NewsListFragment, course: Course) =
        withContext(Dispatchers.IO) {
            if (!Manaba.loggedIn) Manaba.login(Preferences.getCredentials().first, Preferences.getCredentials().second)
            Manaba.getCourseNewses(Manaba.getCourseIdFromURL(course.url))

        }.let {
            newsListFragment.clearList()
            it.forEach {
                newsListFragment.addNews(
                    NewsThumbnailItem(
                        MyApplication.getInstance(),
                        it.title,
                        it.author,
                        SimpleDateFormat("yyyy/MM/dd HH:mm").format(it.updatedAt),
                        course.title,
                        it.newsUrl
                    )
                )
            }
        }

}