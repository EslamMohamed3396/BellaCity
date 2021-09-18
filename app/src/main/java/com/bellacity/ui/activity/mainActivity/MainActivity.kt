package com.bellacity.ui.activity.mainActivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bellacity.R
import com.bellacity.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner
        initViews()


    }


    private fun initViews() {
        navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
            ?: return

        binding.bottomNavigation.setupWithNavController(navHostFragment.navController)


        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            return@setOnNavigationItemSelectedListener onNavItemDestinationSelected(
                item,
                navHostFragment.navController
            )
        }
    }

    private fun onNavItemDestinationSelected(
        item: MenuItem,
        navController: NavController
    ): Boolean {

        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            builder.setPopUpTo(
                R.id.home_button_navigation,
                false
            )
        }
        val options = builder.build()
        return try {
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

}