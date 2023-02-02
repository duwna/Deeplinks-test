package ru.touchin.deeplink.navigation

import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import ru.touchin.deeplink.R

enum class TabType(
    @IdRes val menuId: Int,
    @StringRes val nameId: Int,
    @ColorRes val backgroundColor: Int
) {

    CATALOG(
        menuId = R.id.menu_item_catalog,
        nameId = R.string.title_catalog,
        backgroundColor = R.color.catalog_background
    ),

    CART(
        menuId = R.id.menu_item_cart,
        nameId = R.string.title_cart,
        backgroundColor = R.color.cart_background
    ),

    ORDER(
        menuId = R.id.menu_item_order,
        nameId = R.string.title_order,
        backgroundColor = R.color.order_background
    )
}
