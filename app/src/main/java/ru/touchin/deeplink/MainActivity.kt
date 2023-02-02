package ru.touchin.deeplink

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.touchin.deeplink.databinding.ActivityMainBinding
import ru.touchin.deeplink.di.AppModule
import ru.touchin.deeplink.navigation.Navigator
import ru.touchin.deeplink.navigation.Screens

class MainActivity : AppCompatActivity() {

    private val cicerone by lazy { AppModule.ciceroneHolder.rootCicerone }

    private val navigator by lazy {
        Navigator(
            this,
            R.id.fragment_main_container,
            supportFragmentManager
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater).root)
        cicerone.router.navigateTo(Screens.main())
    }

    override fun onResume() {
        super.onResume()
        cicerone.getNavigatorHolder().setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        cicerone.getNavigatorHolder().removeNavigator()
    }
}
