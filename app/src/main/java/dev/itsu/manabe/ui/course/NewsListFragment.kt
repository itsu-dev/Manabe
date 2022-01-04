package dev.itsu.manabe.ui.course

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import dev.itsu.manabe.R
import dev.itsu.manabe.api.model.Course
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsListFragment : Fragment() {

    private val presenter = NewsListFragmentPresenter()
    private lateinit var course: Course

    companion object {
        fun newInstance(course: Course): Fragment {
            return NewsListFragment().apply {
                this.arguments = Bundle().apply {
                    this.putSerializable("course", course)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        this.course = arguments?.getSerializable("course") as Course
        val layout = inflater.inflate(R.layout.fragment_list, container, false)


        update()
        return layout
    }

    fun clearList() {
        this.view?.findViewById<LinearLayout>(R.id.list_layout)?.removeAllViews()
    }

    fun addNews(item: NewsThumbnailItem) {
        this.view?.findViewById<LinearLayout>(R.id.list_layout)?.addView(item.newInstance())
    }

    fun update() = GlobalScope.launch(Dispatchers.Main) {
        presenter.update(this@NewsListFragment, course)
    }

}