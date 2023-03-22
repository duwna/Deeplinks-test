package ru.touchin.deeplink.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentMainBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.BottomNavigationUtil
import ru.touchin.deeplink.navigation.DeeplinkNavigator.navigateToExternalScreen
import ru.touchin.deeplink.navigation.DeeplinkNavigator.navigateToHomeScreen
import ru.touchin.deeplink.navigation.DeeplinkParser.getBottomNavigationItem
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        BottomNavigationUtil.initBottomNavigation(
            bottomNavigationView = binding.bottomNavigation,
            context = requireContext(),
            fragmentManager = childFragmentManager,
            savedInstanceState = savedInstanceState
        )

        handleDeeplink()

        binding.searchIcon.setOnClickListener {
            SearchDialogFragment().show(childFragmentManager, null)
        }

        binding.stopIcon.setOnClickListener {
            AppModule.deepLinkHandler.cancelAll()
        }
    }

    fun handleBackPressed() {
        when (binding.bottomNavigation.selectedItemId) {
            TabType.CATALOG.menuId -> requireActivity().finish()
            else -> binding.bottomNavigation.selectedItemId = TabType.CATALOG.menuId
        }
    }

    private fun handleDeeplink() {
        AppModule
            .deepLinkHandler
            .observeDeeplink(
                observerOrder = 0,
                isFinalObserver = { it.getBottomNavigationItem() == null },
                navigateAction = { deeplink ->
                    val tabType = deeplink.getBottomNavigationItem()

                    if (tabType != null) {
                        binding.bottomNavigation.selectedItemId = tabType.menuId
                        deeplink.navigateToHomeScreen()
                    } else {
                        deeplink.navigateToExternalScreen()
                    }
                }
            )
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
