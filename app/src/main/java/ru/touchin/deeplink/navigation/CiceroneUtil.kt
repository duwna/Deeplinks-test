package ru.touchin.deeplink.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

class CiceroneHolder {

    val rootCicerone = Cicerone.create()

    private val tabsCicerone = arrayOfNulls<Cicerone<Router>>(TabType.values().size)

    fun ciceroneByTab(tab: TabType): Cicerone<Router> = tabsCicerone.getOrNull(tab.ordinal)
        ?: Cicerone.create().also {
            tabsCicerone[tab.ordinal] = it
        }

}

class Navigator(
    fragmentActivity: FragmentActivity,
    containerId: Int,
    fragmentManager: FragmentManager
) : AppNavigator(fragmentActivity, containerId, fragmentManager) {

    override fun setupFragmentTransaction(
        screen: FragmentScreen,
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment
    ) {
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        super.setupFragmentTransaction(screen, fragmentTransaction, currentFragment, nextFragment)
    }
}
