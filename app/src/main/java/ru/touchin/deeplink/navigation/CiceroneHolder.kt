package ru.touchin.deeplink.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class CiceroneHolder {

    val rootCicerone = Cicerone.create()

    private val tabContainers = HashMap<TabType, Cicerone<Router>>()

    fun getCicerone(key: TabType): Cicerone<Router> =
        tabContainers.getOrPut(key) { Cicerone.create() }

}
