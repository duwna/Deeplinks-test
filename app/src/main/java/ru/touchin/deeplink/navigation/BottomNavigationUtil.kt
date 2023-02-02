package ru.touchin.deeplink.navigation

import android.content.Context
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Lifecycle
import ru.touchin.deeplink.R
import ru.touchin.deeplink.ui.RootFragment

object BottomNavigationUtil {

    fun selectTab(context: Context, fragmentManager: FragmentManager, tab: TabType) {
        val tabTag = context.getString(tab.nameId)

        val currentFragment = fragmentManager.fragments.find { it.isVisible }

        val newFragment = fragmentManager.findFragmentByTag(tabTag)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = fragmentManager.beginTransaction()

        if (newFragment == null) {
            transaction.add(
                R.id.nav_host_fragment_activity_main,
                RootFragment.newInstance(RootFragment.NavArgs(tab)),
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
