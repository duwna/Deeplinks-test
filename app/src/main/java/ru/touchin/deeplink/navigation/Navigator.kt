package ru.touchin.deeplink.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.github.terrakok.cicerone.androidx.FragmentScreen

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
