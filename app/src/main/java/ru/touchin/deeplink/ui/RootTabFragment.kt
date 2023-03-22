package ru.touchin.deeplink.ui

import android.os.Parcelable
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import kotlinx.parcelize.Parcelize
import ru.touchin.deeplink.R
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.DEFAULT_FRAGMENT_ARGS_KEY
import ru.touchin.deeplink.navigation.Navigator
import ru.touchin.deeplink.navigation.Screens
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.deeplink.ui.tabs.HomeFragment
import ru.touchin.extensions.args
import ru.touchin.extensions.withParcelable

class RootTabFragment : Fragment(R.layout.fragment_root) {

    @Parcelize
    data class NavArgs(val tabType: TabType) : Parcelable

    companion object {
        fun newInstance(args: NavArgs) = RootTabFragment()
            .withParcelable(DEFAULT_FRAGMENT_ARGS_KEY, args)
    }

    private val args by args<NavArgs>(DEFAULT_FRAGMENT_ARGS_KEY)
    private val cicerone by lazy { AppModule.ciceroneHolder.ciceroneByTab(args.tabType) }

    private val navigator by lazy {
        Navigator(
            requireActivity(),
            R.id.fragment_root_container,
            childFragmentManager
        )
    }

    override fun onStart() {
        super.onStart()
        if (childFragmentManager.fragments.isEmpty()) {
            cicerone.router.navigateTo(Screens.home(HomeFragment.NavArgs(args.tabType)))
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (childFragmentManager.backStackEntryCount > 1) {
                    cicerone.router.exit()
                } else {
                    (parentFragment as? MainFragment)?.handleBackPressed()
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.getNavigatorHolder().removeNavigator()
    }
}
