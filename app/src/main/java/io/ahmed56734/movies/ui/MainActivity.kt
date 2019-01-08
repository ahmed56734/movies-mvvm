package io.ahmed56734.movies.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import io.ahmed56734.movies.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        with(findNavController(R.id.navHostFragment)) {
            val appBarConfiguration = AppBarConfiguration(this.graph, drawerLayout)
            toolbar.setupWithNavController(this, appBarConfiguration)
            NavigationUI.setupWithNavController(navigationView, this)
        }

    }


    override fun onNavigateUp(): Boolean {
        return findNavController(R.id.navHostFragment).navigateUp(drawerLayout)
    }
}
