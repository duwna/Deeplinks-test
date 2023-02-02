package ru.touchin.deeplink.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentMainBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.BottomNavigationUtil
import ru.touchin.deeplink.navigation.Screens
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding by viewBinding(FragmentMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleDeeplink()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomNavigation(savedInstanceState)

        binding.searchIcon.setOnClickListener {
            SearchDialogFragment().show(childFragmentManager, null)
        }
    }

    private fun initBottomNavigation(savedInstanceState: Bundle?) {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            BottomNavigationUtil.selectTab(
                context = requireContext(),
                fragmentManager = childFragmentManager,
                tab = TabType.values().find { it.menuId == menuItem.itemId } ?: error("unknown bottom menu itemId")
            )
            true
        }

        if (savedInstanceState == null && childFragmentManager.fragments.isEmpty()) {
            binding.bottomNavigation.selectedItemId = TabType.CATALOG.menuId
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
            .observeDeeplink()
            .onEach { deeplink ->

                val tabType = TabType.values().find { tab ->
                    deeplink.deepLink.contains(getString(tab.nameId), ignoreCase = true)
                }

                withContext(Dispatchers.Main) {
                    if (tabType != null) {
                        binding.bottomNavigation.selectedItemId = tabType.menuId
                    } else {
                        AppModule.ciceroneHolder.rootCicerone.router
                            .navigateTo(Screens.webView(WebViewFragment.NavArgs(deeplink.deepLink)))
                    }
                }
            }
            .launchIn(CoroutineScope(Job()))
    }
}
