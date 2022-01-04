package dev.itsu.manabe.ui.main.home

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

class HomeFragment : Fragment() {

    private lateinit var homeFragment: View
    private val presenter = HomeFragmentPresenter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        homeFragment = inflater.inflate(R.layout.fragment_home, container, false)

        GlobalScope.launch(Dispatchers.Main) {
            presenter.updateAssignments()
            presenter.updateCurrentCourse()
        }

        return homeFragment
    }

    fun addCurrentSubjectThumbnail(thumbnail: CurrentSubjectThumbnail) {
        val homeLayout = homeFragment.findViewById<LinearLayout>(R.id.home_layout)
        homeLayout.addView(thumbnail.newInstance(), 0)
    }

    fun addAssignmentsThumbnail(thumbnail: AssignmentThumbnail) {
        val homeLayout = homeFragment.findViewById<LinearLayout>(R.id.home_layout)
        homeLayout.addView(thumbnail.newInstance(), 0)
    }

}