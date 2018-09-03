package com.example.abdurrahman.footballapps.ui.team.detailteams

import android.database.sqlite.SQLiteConstraintException
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.id.add_to_favorite
import com.example.abdurrahman.footballapps.database.database
import com.example.abdurrahman.footballapps.model.Favorite
import com.example.abdurrahman.footballapps.model.Teams
import com.example.abdurrahman.footballapps.ui.team.detailteams.overview.OverviewFragment
import com.example.abdurrahman.footballapps.ui.team.detailteams.players.PlayersFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_teams.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import java.util.ArrayList

class DetailTeamsActivity : AppCompatActivity() {

    private lateinit var teams: Teams
    private var menuItem: Menu? = null

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_teams)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detail_teams_toolbar.navigationIcon = getDrawable(R.drawable.ic_arrow_back_24dp)
        }
        setSupportActionBar(detail_teams_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        teams = intent.getParcelableExtra("teams_detail")

        favoriteState()

        setupViewPager(detail_teams_viewpager)
        detail_teams_tabs.setupWithViewPager(detail_teams_viewpager)

        Picasso.get().load(teams.teamBadge).placeholder(R.drawable.load).error(R.drawable.error).into(detail_teams_logo)
        detail_teams_name.text = teams.teamName
        detail_teams_year.text = teams.intYear
        detail_teams_stadium.text = teams.strStadium

    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE_TEAMS)
                    .whereArgs("(TEAMS_ID = {id})",
                            "id" to teams.teamId.toString())
            val favorite = result.parseList(classParser<Teams>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE_TEAMS,
                        Favorite.TEAMS_ID to teams.teamId,
                        Favorite.TEAM_NAME to teams.teamName,
                        Favorite.TEAM_DESC to teams.strDesc,
                        Favorite.TEAM_YEAR to teams.intYear,
                        Favorite.TEAM_STADIUM to teams.strStadium,
                        Favorite.TEAM_BADGE to teams.teamBadge)
            }
            snackbar(maincontent, getString(R.string.msg_snackbar_add_fav)).show()
        }catch (e: SQLiteConstraintException) {
            snackbar(maincontent, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE_TEAMS,"(TEAMS_ID = {id})",
                        "id" to teams.teamId.toString())
            }
            snackbar(maincontent, getString(R.string.msg_snackbar_remove_fav)).show()
        } catch (e: SQLiteConstraintException){
            snackbar(maincontent, e.localizedMessage).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun setupViewPager(viewPager: ViewPager){
        val pagerAdapter = TeamsPagerAdapter(teams.teamId.toString(),teams.strDesc.toString(),
                supportFragmentManager,2)
        pagerAdapter.addFrag(OverviewFragment(), "Overview")
        pagerAdapter.addFrag(PlayersFragment(), "Player")
        viewPager.adapter = pagerAdapter
    }

    internal inner class TeamsPagerAdapter(private val idTeams: String, private val descTeams: String,
                                           fm: FragmentManager, private var tabCount: Int)
        : FragmentPagerAdapter(fm) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment? {
            return when(position) {
                0 -> OverviewFragment.newInstance(descTeams)
                1 -> PlayersFragment.newInstance(idTeams)
                else -> null
            }
        }

        override fun getCount(): Int {
            return tabCount
        }

        fun addFrag(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }
}