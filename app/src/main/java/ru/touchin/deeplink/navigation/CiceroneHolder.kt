package ru.touchin.deeplink.navigation

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router

class CiceroneHolder {

    private val containers = HashMap<TabType, Cicerone<Router>>()

    fun getCicerone(key: TabType): Cicerone<Router> =
        containers.getOrPut(key) { Cicerone.create() }

}
