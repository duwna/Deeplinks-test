package ru.touchin.deeplink.navigation

import android.view.View
import androidx.core.view.isVisible
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.Screen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.DeeplinkParser.getBottomNavigationItem
import ru.touchin.deeplink.navigation.DeeplinkParser.getItemId
import ru.touchin.deeplink.navigation.DeeplinkParser.isListDeeplink
import ru.touchin.deeplink.ui.WebViewFragment
import ru.touchin.deeplink.ui.tabs.DetailsFragment
import ru.touchin.deeplink.ui.tabs.HomeFragment
import ru.touchin.deeplink.ui.tabs.ListFragment
import ru.touchin.roboswag.deeplink_utils.DeeplinkModel

object DeeplinkNavigator {

    suspend fun imitateNetworkRequest(loader: View) {
        loader.isVisible = true

        withContext(Dispatchers.IO) {
            try {
                delay(3000)
            } finally {
                withContext(Dispatchers.Main) {
                    loader.isVisible = false
                }
            }
        }
    }

    fun DeeplinkModel.navigateToScreenWithChain() = navigate { tabType, router ->
        val screenChain = buildList<Screen> {
            add(Screens.home(HomeFragment.NavArgs(tabType)))

            if (isListDeeplink()) {
                add(Screens.list(ListFragment.NavArgs(tabType)))
            }

            getItemId()?.let { id ->
                add(Screens.details(DetailsFragment.NavArgs(tabType, id)))
            }
        }

        router.newRootChain(*screenChain.toTypedArray())
    }

    fun DeeplinkModel.navigateToHomeScreen() = navigate { tabType, router ->
        router.backTo(Screens.home(HomeFragment.NavArgs(tabType)))
    }

    fun DeeplinkModel.navigateToListScreen() = navigate { tabType, router ->
        router.navigateTo(Screens.list(ListFragment.NavArgs(tabType)))
    }

    fun DeeplinkModel.navigateToDetailsScreen() = navigate { tabType, router ->
        val id = requireNotNull(getItemId())
        router.navigateTo(Screens.details(DetailsFragment.NavArgs(tabType, id)))
    }

    fun DeeplinkModel.navigateToExternalScreen() {
        AppModule.ciceroneHolder.rootCicerone.router
            .navigateTo(Screens.webView(WebViewFragment.NavArgs(url)))
    }

    private fun DeeplinkModel.navigate(action: (TabType, Router) -> Unit) {
        val tabType = requireNotNull(getBottomNavigationItem())
        val router = AppModule.ciceroneHolder.ciceroneByTab(tabType).router

        action.invoke(tabType, router)
    }
}
