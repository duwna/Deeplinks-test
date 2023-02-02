package ru.touchin.deeplink

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ru.touchin.deeplink.databinding.ActivityMainBinding
import ru.touchin.deeplink.navigation.BottomNavigationUtil
import ru.touchin.deeplink.navigation.TabType
import ru.touchin.deeplink.ui.SearchDialogFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomNavigation(savedInstanceState)
    }

    private fun initBottomNavigation(savedInstanceState: Bundle?) {
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            BottomNavigationUtil.selectTab(
                context = applicationContext,
                fragmentManager = supportFragmentManager,
                tab = TabType.values().find { it.menuId == menuItem.itemId } ?: error("unknown bottom menu itemId")
            )
            true
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = TabType.CATALOG.menuId
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.action_bar_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_bar_search -> SearchDialogFragment().show(supportFragmentManager, null)
        }

        return super.onOptionsItemSelected(item)
    }

    fun handleBackPress() {
        when (binding.bottomNavigation.selectedItemId) {
            TabType.CATALOG.menuId -> finish()
            else -> binding.bottomNavigation.selectedItemId = TabType.CATALOG.menuId
        }
    }
}
