package com.amazonaws.matches.matchesList.matchesTab

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.amazonaws.common.parcelableArrayList
import com.amazonaws.common.serializable
import com.amazonaws.matches.R
import com.amazonaws.matches.databinding.MatchesRecyclerViewBinding
import com.amazonaws.matches.matchesList.MatchesItemForListView
import com.amazonaws.matches.matchesList.adapter.MatchesAdapter
import com.amazonaws.model.MatchesModel
import com.amazonaws.navigation.NavigationScreenKey
import com.amazoneaws.timeschedule.startTimeScheduleForMatch
import com.google.android.material.snackbar.Snackbar
import com.amazonaws.navigation.R as navR

class MatchTabFragment : Fragment() {
    companion object {
        const val MATCHES_BUNDLE_KEY = "MatchTabFragment.MATCHES_BUNDLE_KEY"
        const val MATCHES_TYPE_BUNDLE_KEY = "MatchTabFragment.MATCHES_TYPE_BUNDLE_KEY"
        fun getFragmentInstance(matches: List<MatchesModel>?, matchesType: MatchesType) =
            MatchTabFragment().apply {
                matches?.let {
                    arguments = Bundle().apply {
                        putParcelableArrayList(
                            MATCHES_BUNDLE_KEY,
                            arrayListOf<Parcelable?>().apply {
                                addAll(matches)
                            })
                        putSerializable(MATCHES_TYPE_BUNDLE_KEY, matchesType)
                    }
                }
            }
    }

    private var viewBinding: MatchesRecyclerViewBinding? = null

    private val matchesAdapter = MatchesAdapter(onHighlightsClickCallback = {
        goToHighlightVideo(it)
    }, onScheduleClickCallback = {
        startScheduleForMatch(it)
    })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = MatchesRecyclerViewBinding.inflate(
            inflater, container,
            false
        )
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.rcMatches?.adapter = matchesAdapter

        val matches = arguments?.parcelableArrayList<MatchesModel>(MATCHES_BUNDLE_KEY)
        val matchesType = arguments?.serializable<MatchesType>(MATCHES_TYPE_BUNDLE_KEY)
        if (matches != null && matchesType != null) {
            matchesAdapter.submitList(matches.map {
                MatchesItemForListView(it, matchesType)
            })
        }
        if (matchesType == MatchesType.PREVIOUS) {
            viewBinding?.rcMatches?.tag = MatchesType.PREVIOUS.name
        } else {
            viewBinding?.rcMatches?.tag = MatchesType.UPCOMING.name
        }

        requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                if (!it) {
                    Snackbar.make(
                        viewBinding?.root ?: return@registerForActivityResult,
                        resources.getString(R.string.text_notification_per),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>


    private fun startScheduleForMatch(matchesModel: MatchesModel) {
        context?.let {
            val triggerSchedule = {
                this.activity?.applicationContext?.let { appContext ->
                    startTimeScheduleForMatch(
                        appContext,
                        matchesModel
                    )
                    Snackbar.make(
                        viewBinding?.root ?: return@let,
                        resources.getString(R.string.text_schedule_set),
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                if (ContextCompat.checkSelfPermission(
                        it,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    triggerSchedule.invoke()
                } else {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            } else {
                triggerSchedule.invoke()
            }
        }
    }


    private fun goToHighlightVideo(matches: MatchesModel) {
        getRootNav()?.navigate(
            com.amazonaws.navigation.R.id.action_homeWrapperFragment_to_highlightPlayingFragment,
            bundleOf(NavigationScreenKey.MATCH_HIGHLIGHT_VIDEO_URL to matches.highlights)
        )
    }

    private fun getRootNav(): NavController? {
        val navHostFragment = activity?.supportFragmentManager?.findFragmentById(com.amazonaws.navigation.R.id.nav_root) as? NavHostFragment
        return navHostFragment?.navController
    }
}