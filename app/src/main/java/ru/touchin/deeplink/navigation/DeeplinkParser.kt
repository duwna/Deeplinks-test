package ru.touchin.deeplink.navigation

import ru.touchin.roboswag.deeplink_utils.DeeplinkModel

object DeeplinkParser {

    fun DeeplinkModel.getBottomNavigationItem(): TabType? =
        TabType.values().find { tab ->
            partsParts?.firstOrNull()?.contains(tab.name, ignoreCase = true) == true
        }

    fun DeeplinkModel.getItemId(): Int? =
        partsParts?.lastOrNull()?.toIntOrNull()

    fun DeeplinkModel.isListDeeplink(): Boolean =
        partsParts?.getOrNull(1) == "list"

}

val ExampleDeepLinkUrls = listOf(
    "https://example.com/catalog",
    "https://example.com/catalog/list",
    "https://example.com/catalog/list/1",

    "https://example.com/cart",
    "https://example.com/cart/list",
    "https://example.com/cart/list/1",

    "https://example.com/order",
    "https://example.com/order/list",
    "https://example.com/order/list/1",

    "https://www.lipsum.com/",
)
