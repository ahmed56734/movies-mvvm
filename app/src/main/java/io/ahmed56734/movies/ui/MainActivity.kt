package io.ahmed56734.movies.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.miguelcatalan.materialsearchview.MaterialSearchView
import io.ahmed56734.movies.R
import io.ahmed56734.movies.ui.search.SearchViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val searchViewModel: SearchViewModel by viewModel()

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        navController = findNavController(R.id.navHostFragment)
        toolbar.title = navController.currentDestination?.label
        val appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        navigationView.setupWithNavController(navController)


        initViews()

        searchViewModel.recentQueries.observe(this, Observer {
            searchView.setSuggestions(it.toTypedArray())
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val searchMenuItem = menu?.findItem(R.id.action_search)!!
        searchView.setMenuItem(searchMenuItem)
        return true
    }

    private fun initViews() {

        searchView.showSearch(false)
        searchView.closeSearch()

        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
                    searchViewModel.search(query)
                    searchView.closeSearch()
                    navController.navigate(R.id.action_global_searchResultsFragment)
                    return true
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


    override fun onBackPressed() {
        if (searchView.isSearchOpen) {
            searchView.closeSearch()
        } else {
            super.onBackPressed()
        }
    }

}
