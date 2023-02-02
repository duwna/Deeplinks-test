package ru.touchin.deeplink.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.touchin.deeplink.ui.tabs.DetailsFragment
import ru.touchin.deeplink.ui.tabs.HomeFragment
import ru.touchin.deeplink.ui.tabs.ListFragment

object Screens {

    fun home(args: HomeFragment.NavArgs) = FragmentScreen { HomeFragment.newInstance(args) }

    fun list(args: ListFragment.NavArgs) = FragmentScreen { ListFragment.newInstance(args) }

    fun details(args: DetailsFragment.NavArgs) = FragmentScreen { DetailsFragment.newInstance(args) }
}
