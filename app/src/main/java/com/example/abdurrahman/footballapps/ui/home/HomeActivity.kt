package com.example.abdurrahman.footballapps.ui.home

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.id.*
import com.example.abdurrahman.footballapps.ui.match.MatchFragment
import com.example.abdurrahman.footballapps.ui.team.TeamsFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.elevation = 0F

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                match -> {
                    loadMatchFragment(savedInstanceState)
                }
                teams -> {
                    loadTeamsFragment(savedInstanceState)
                }
                favorites-> {
//                    loadFavoritesFragment(savedInstanceState)
                }

            }
            true
        }
        bottom_navigation.selectedItemId = match
    }

    private fun loadMatchFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, MatchFragment(), MatchFragment::class.simpleName)
                    .commit()
        }
    }

    private fun loadTeamsFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.main_container, TeamsFragment(), TeamsFragment::class.simpleName)
                    .commit()
        }
    }
}
