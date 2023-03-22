package ru.touchin.deeplink.ui.tabs

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.parcelize.Parcelize
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentHomeBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.DEFAULT_FRAGMENT_ARGS_KEY
import ru.touchin.deeplink.navigation.DeeplinkNavigator
import ru.touchin.deeplink.navigation.DeeplinkNavigator.navigateToListScreen
import ru.touchin.deeplink.navigation.DeeplinkParser.getBottomNavigationItem
import ru.touchin.deeplink.navigation.DeeplinkParser.isListDeeplink
import ru.touchin.deeplink.navigation.Screens
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.extensions.args
import ru.touchin.extensions.withParcelable
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class HomeFragment : Fragment(R.layout.fragment_home) {

    @Parcelize
    data class NavArgs(val tabType: TabType) : Parcelable

    companion object {
        fun newInstance(args: NavArgs) = HomeFragment()
            .withParcelable(DEFAULT_FRAGMENT_ARGS_KEY, args)
    }

    private val args by args<NavArgs>(DEFAULT_FRAGMENT_ARGS_KEY)
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val cicerone by lazy { AppModule.ciceroneHolder.ciceroneByTab(args.tabType) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleDeeplink()

        binding.screenName.setText(args.tabType.nameId)

        binding.openListButton.setOnClickListener {
            cicerone.router.navigateTo(Screens.list(ListFragment.NavArgs(args.tabType)))
        }
    }

    private fun handleDeeplink() {
        AppModule
            .deepLinkHandler
            .observeDeeplink(
                observerOrder = 1,
                canHandle = { it.getBottomNavigationItem() == args.tabType },
                isFinalObserver = { !it.isListDeeplink() },
                navigateCondition = { it.isListDeeplink() },
                navigateAction = {
                    DeeplinkNavigator.imitateNetworkRequest(binding.loader)
                    it.navigateToListScreen()
                }
            )
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
