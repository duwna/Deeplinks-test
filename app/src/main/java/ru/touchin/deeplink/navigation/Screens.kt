package ru.touchin.deeplink.navigation

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.touchin.deeplink.R
import ru.touchin.deeplink.ui.MainFragment
import ru.touchin.deeplink.ui.WebViewFragment
import ru.touchin.deeplink.ui.tabs.DetailsFragment
import ru.touchin.deeplink.ui.tabs.HomeFragment
import ru.touchin.deeplink.ui.tabs.ListFragment

const val DEFAULT_FRAGMENT_ARGS_KEY = "DEFAULT_ARGS_KEY"

object Screens {

    fun main() = FragmentScreen { MainFragment() }

    fun home(args: HomeFragment.NavArgs) = FragmentScreen { HomeFragment.newInstance(args) }

    fun list(args: ListFragment.NavArgs) = FragmentScreen { ListFragment.newInstance(args) }

    fun details(args: DetailsFragment.NavArgs) = FragmentScreen { DetailsFragment.newInstance(args) }

    fun webView(args: WebViewFragment.NavArgs) = FragmentScreen { WebViewFragment.newInstance(args) }
}

enum class TabType(
    @IdRes val menuId: Int,
    @StringRes val nameId: Int
) {

    CATALOG(
        menuId = R.id.menu_item_catalog,
        nameId = R.string.title_catalog,

        ),

    CART(
        menuId = R.id.menu_item_cart,
        nameId = R.string.title_cart,
    ),

    ORDER(
        menuId = R.id.menu_item_order,
        nameId = R.string.title_order,
    )
}
