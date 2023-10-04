package com.amazonaws.matches.matchesList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.amazonaws.common.DataConstant.TEAM_ID_FOR_MATCH_LISTENER
import com.amazonaws.common.gone
import com.amazonaws.common.goneIf
import com.amazonaws.common.visible
import com.amazonaws.matches.R
import com.amazonaws.matches.databinding.MatchesFragmentBinding
import com.amazonaws.matches.matchesList.adapter.MatchesTabAdapter
import com.amazonaws.navigation.NavigationScreenKey.TEAM_ID_FOR_MATCH_KEY
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import com.amazonaws.navigation.R as navR

class MatchesFragment : Fragment() {


    private var viewBinding: MatchesFragmentBinding? = null
    private val viewModel: MatchesViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val teamIdFromExtra =
            arguments?.getString(TEAM_ID_FOR_MATCH_KEY)
        setMatchesFilterType(teamIdFromExtra.orEmpty())
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = MatchesFragmentBinding.inflate(
            inflater, container,
            false
        )
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observableData()
        setFragmentResultListener(TEAM_ID_FOR_MATCH_LISTENER) { _, bundle ->
            val teamIdFromExtra =
                bundle.getString(TEAM_ID_FOR_MATCH_KEY)
            setMatchesFilterType(teamIdFromExtra.orEmpty())
            viewModel.reloadTeamById()
        }
        viewBinding?.ivBack.goneIf(viewModel.hideBackView() && !resources.getBoolean(navR.bool.isTablet))
        viewBinding?.ivBack?.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setMatchesFilterType(teamId: String) {
        viewModel.setMatchesFilterType(teamId)
    }

    private fun observableData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.matchesState.collect {
                    when (it) {
                        is MatchesUiState.Loading -> {
                            viewBinding?.matchesLoading?.visible()
                        }

                        is MatchesUiState.EmptyList -> {
                            viewBinding?.matchesLoading?.gone()
                        }

                        is MatchesUiState.Success -> {
                            viewBinding?.matchesLoading?.gone()
                            initViewPagers(it)
                        }

                        else -> {

                        }
                    }
                }
            }
        }
    }

    private fun initViewPagers(state: MatchesUiState.Success) {
        val pagerAdapter = MatchesTabAdapter(
            this@MatchesFragment,
            state.matches?.previous,
            state.matches?.upComing
        )
        viewBinding?.matchesPager?.adapter = pagerAdapter
        TabLayoutMediator(
            viewBinding?.matchesTabLayout ?: return,
            viewBinding?.matchesPager ?: return
        ) { tab, position ->
            tab.text = when (position) {
                0 -> resources.getString(R.string.text_previous_matches)
                1 -> resources.getString(R.string.text_upcoming_matches)
                else -> ""
            }
        }.attach()
    }
}