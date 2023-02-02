package ru.touchin.deeplink.navigation

import androidx.annotation.IdRes
import androidx.annotation.StringRes
import ru.touchin.deeplink.R

enum class TabType(
    @IdRes val menuId: Int,
    @StringRes val nameId: Int
) {

    CATALOG(
        menuId = R.id.menu_item_catalog,
        nameId = R.string.title_catalog
    ),

    CART(
        menuId = R.id.menu_item_cart,
        nameId = R.string.title_cart
    ),

    ORDER(
        menuId = R.id.menu_item_order,
        nameId = R.string.title_order
    )
}
