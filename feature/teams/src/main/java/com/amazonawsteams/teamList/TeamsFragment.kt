package com.amazonawsteams.teamList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.amazonaws.common.DataConstant.TEAM_ID_FOR_MATCH_LISTENER
import com.amazonaws.common.gone
import com.amazonaws.common.visible
import com.amazonaws.model.TeamsModel
import com.amazonaws.navigation.NavigationScreenKey.TEAM_ID_FOR_MATCH_KEY
import com.amazonaws.navigation.R
import com.amazonawsteams.databinding.TeamsFragmentBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import com.amazonaws.navigation.R as navR

class TeamsFragment : Fragment() {
    private var viewBinding: TeamsFragmentBinding? = null
    private val viewModel: TeamsViewModel by inject()
    private val teamsAdapter = TeamsAdapter {
        moveToTeamDetailScreen(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = TeamsFragmentBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding?.rcTeams?.adapter = teamsAdapter
        observableData()
    }

    private fun observableData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.teamState.collect {
                    when (it) {
                        is TeamsUiState.Loading -> {
                            viewBinding?.teamsLoading?.visible()
                        }

                        is TeamsUiState.EmptyList -> {
                            viewBinding?.teamsLoading?.gone()
                            viewBinding?.tvEmpty?.visibility = View.VISIBLE
                        }

                        is TeamsUiState.Success -> {
                            viewBinding?.teamsLoading?.gone()
                            teamsAdapter.submitList(it.teams)
                        }

                        else -> {
                            //nothing
                        }
                    }
                }
            }
        }
    }

    private fun moveToTeamDetailScreen(team: TeamsModel) {
        if (resources.getBoolean(navR.bool.isTablet)) {
            getDetailTabletTabHost()?.childFragmentManager?.fragments?.firstOrNull()
                ?.setFragmentResult(
                    TEAM_ID_FOR_MATCH_LISTENER,
                    bundleOf(TEAM_ID_FOR_MATCH_KEY to team.id.orEmpty())
                )
        } else {
            team.id?.let {
                findNavController().navigate(
                    navR.id.action_teamsFragment_to_matchesFragment,
                    bundleOf(TEAM_ID_FOR_MATCH_KEY to it)
                )
            }
        }
    }

    private fun getDetailTabletTabHost(): NavHostFragment? {
        return parentFragment?.parentFragment?.childFragmentManager?.findFragmentById(R.id.nav_right_detail) as? NavHostFragment
    }
}