package com.fox.alyxnews.ui

import android.annotation.TargetApi
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.fox.alyxnews.R
import com.fox.alyxnews.util.makeSnackbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {


    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Nav Host Fragment
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return
        navController = host.navController

        // Sidebar
        val sideBar = findViewById<NavigationView>(R.id.nav_view)
        sideBar?.setupWithNavController(navController)

        // Toolbar
        appBarConfiguration = AppBarConfiguration(
            navController.graph,
            drawerLayout = drawer_layout
        )

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)
        supportActionBar?.setDisplayShowTitleEnabled(false)
//        initViewModel()
        initUi()
        // sub Observer
//        subscribeObserver()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item) {
            item -> makeSnackbar("${item.title}") //makeToast("${item.title}") //Toast.makeText(this, "${item.title}", Toast.LENGTH_SHORT).show()
        }

        return item.onNavDestinationSelected(navController) ||
                super.onOptionsItemSelected(item)
    }

    private fun initUi() {
        settings_button.setOnClickListener {
            navController.navigate(R.id.settingsFragment)
        }

        val newsSpinner = arrayOf("Технологий", "Биткойн", "Бизнес")
        val adapterSpinner = ArrayAdapter(
            this,
            R.layout.simple_spinner_item_custom,
            newsSpinner
        )
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapterSpinner
//        spinner.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    private fun showProgressBar(isVisible: Boolean) {
        if (isVisible) progress_bar.visibility = View.VISIBLE
        else progress_bar.visibility = View.INVISIBLE
    }

    companion object {
        private val TAG = MainActivity::class.java.simpleName
    }
}
