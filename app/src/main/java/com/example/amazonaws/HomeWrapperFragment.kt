package com.example.amazonaws

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.amazonaws.databinding.FragmentHomeWrapperBinding

class HomeWrapperFragment : Fragment() {
    private lateinit var navigationController: NavController
    private lateinit var viewBinding: FragmentHomeWrapperBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeWrapperBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!resources.getBoolean(com.amazonaws.navigation.R.bool.isTablet)) {
            val navHostFragment =
                childFragmentManager.findFragmentById(com.amazonaws.navigation.R.id.nav_host_fragment) as NavHostFragment
            navigationController = navHostFragment.navController
            viewBinding.bottomNav?.apply {
                setupWithNavController(navigationController)
            }
        }
    }
}