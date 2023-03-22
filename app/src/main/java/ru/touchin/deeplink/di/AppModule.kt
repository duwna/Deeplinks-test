package ru.touchin.deeplink.di

import ru.touchin.deeplink.navigation.CiceroneHolder
import ru.touchin.roboswag.deeplink_utils.DeeplinkHandler

object AppModule {

    val ciceroneHolder = CiceroneHolder()

    val deepLinkHandler = DeeplinkHandler()

}
