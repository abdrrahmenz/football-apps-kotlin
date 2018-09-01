package com.example.abdurrahman.footballapps.ui.team.detailteams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.model.Teams
import com.example.abdurrahman.footballapps.ui.team.detailteams.overview.OverviewFragment
import com.example.abdurrahman.footballapps.ui.team.detailteams.players.PlayersFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_teams.*
import java.util.ArrayList

class DetailTeamsActivity : AppCompatActivity() {

    private lateinit var teams: Teams

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_teams)

        teams = intent.getParcelableExtra("teams_detail")

        setSupportActionBar(detail_teams_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""

        setupViewPager(detail_teams_viewpager)
        detail_teams_tabs.setupWithViewPager(detail_teams_viewpager)

        Picasso.get().load(teams.teamBadge).placeholder(R.drawable.load).error(R.drawable.error).into(detail_teams_logo)
        detail_teams_name.text = teams.teamName
        detail_teams_year.text = teams.intYear
        detail_teams_stadium.text = teams.strStadium

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