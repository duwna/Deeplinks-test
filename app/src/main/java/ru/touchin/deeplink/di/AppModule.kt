package ru.touchin.deeplink.di

import ru.touchin.deeplink.navigation.CiceroneHolder
import ru.touchin.roboswag.deeplink_utils.DeeplinkHandler
import ru.touchin.roboswag.deeplink_utils.DeeplinkHandlerImpl

object AppModule {

    val ciceroneHolder = CiceroneHolder()

    val deepLinkHandler: DeeplinkHandler = DeeplinkHandlerImpl()

}
