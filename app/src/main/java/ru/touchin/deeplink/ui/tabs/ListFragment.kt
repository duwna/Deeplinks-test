package ru.touchin.deeplink.ui.tabs

import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.parcelize.Parcelize
import ru.touchin.deeplink.R
import ru.touchin.deeplink.databinding.FragmentListBinding
import ru.touchin.deeplink.databinding.ItemListBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.DEFAULT_FRAGMENT_ARGS_KEY
import ru.touchin.deeplink.navigation.DeeplinkNavigator
import ru.touchin.deeplink.navigation.DeeplinkNavigator.navigateToDetailsScreen
import ru.touchin.deeplink.navigation.DeeplinkParser.getBottomNavigationItem
import ru.touchin.deeplink.navigation.DeeplinkParser.getItemId
import ru.touchin.deeplink.navigation.DeeplinkParser.isListDeeplink
import ru.touchin.deeplink.navigation.Screens
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.extensions.args
import ru.touchin.extensions.withParcelable
import ru.touchin.roboswag.navigation_base.fragments.viewBinding

class ListFragment : Fragment(R.layout.fragment_list) {

    @Parcelize
    data class NavArgs(val tabType: TabType) : Parcelable

    companion object {
        fun newInstance(args: NavArgs) = ListFragment()
            .withParcelable(DEFAULT_FRAGMENT_ARGS_KEY, args)
    }

    private val args by args<NavArgs>(DEFAULT_FRAGMENT_ARGS_KEY)
    private val binding by viewBinding(FragmentListBinding::bind)
    private val cicerone by lazy { AppModule.ciceroneHolder.ciceroneByTab(args.tabType) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleDeeplink()

        binding.screenName.setText(args.tabType.nameId)

        repeat(5) { index ->
            val textView = ItemListBinding.inflate(layoutInflater).root.apply {
                text = getString(R.string.item_list_template, index)
                setOnClickListener {
                    cicerone.router.navigateTo(Screens.details(DetailsFragment.NavArgs(args.tabType, index)))
                }
            }

            binding.listContainer.addView(textView)
        }
    }

    private fun handleDeeplink() {
        AppModule
            .deepLinkHandler
            .observeDeeplink(
                observerOrder = 2,
                canHandle = { it.getBottomNavigationItem() == args.tabType && it.isListDeeplink() },
                navigateCondition = { it.getItemId() != null },
                navigateAction = {
                    DeeplinkNavigator.imitateNetworkRequest(binding.loader)
                    it.navigateToDetailsScreen()
                }
            )
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }
}
