package ru.touchin.deeplink.navigation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.touchin.deeplink.R
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.ui.RootTabFragment
import ru.touchin.deeplink.ui.tabs.HomeFragment

object BottomNavigationUtil {

    fun initBottomNavigation(
        bottomNavigationView: BottomNavigationView,
        context: Context,
        fragmentManager: FragmentManager,
        savedInstanceState: Bundle?
    ) {
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val tabType = TabType.values().find { it.menuId == menuItem.itemId }
                ?: error("unknown bottom menu itemId")

            val isFirstFragmentAttached = fragmentManager.fragments.isNotEmpty()

            if (isFirstFragmentAttached && menuItem.itemId == bottomNavigationView.selectedItemId) {
                val initialScreen = Screens.home(HomeFragment.NavArgs(tabType))
                AppModule.ciceroneHolder.ciceroneByTab(tabType).router.backTo(initialScreen)
            } else {
                selectTab(
                    context = context,
                    fragmentManager = fragmentManager,
                    tab = tabType
                )
            }

            true
        }

        if (savedInstanceState == null && fragmentManager.fragments.isEmpty()) {
            bottomNavigationView.selectedItemId = TabType.CATALOG.menuId
        }
    }

    private fun selectTab(context: Context, fragmentManager: FragmentManager, tab: TabType) {
        val tabTag = context.getString(tab.nameId)

        val currentFragment = fragmentManager.fragments.find { it.isVisible }

        val newFragment = fragmentManager.findFragmentByTag(tabTag)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = fragmentManager.beginTransaction()

        if (newFragment == null) {
            transaction.add(
                R.id.nav_host_fragment_activity_main,
                RootTabFragment.newInstance(RootTabFragment.NavArgs(tab)),
                tabTag
            )
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment).setMaxLifecycle(currentFragment, Lifecycle.State.CREATED)
        }

        if (newFragment != null) {
            transaction.show(newFragment).setMaxLifecycle(newFragment, Lifecycle.State.RESUMED)
        }

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)

        transaction.commitNow()
    }
}
