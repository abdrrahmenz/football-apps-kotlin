package com.example.abdurrahman.footballapps.ui.team.detailteams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.ui.team.detailteams.overview.OverviewFragment
import com.example.abdurrahman.footballapps.ui.team.detailteams.players.PlayersFragment
import kotlinx.android.synthetic.main.activity_detail_teams.*
import java.util.ArrayList

class DetailTeamsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_teams)

        supportActionBar?.hide()

        setupViewPager(detail_teams_viewpager)
        detail_teams_tabs.setupWithViewPager(detail_teams_viewpager)

        detail_teams_collapse_toolbar.title = "Detail Teams"
        detail_teams_collapse_toolbar.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary))
//        detail_teams_collapse_toolbar.setExpandedTitleTextAppearance(R.)

    }

    fun setupViewPager(viewPager: ViewPager){
        val pagerAdapter = TeamsPagerAdapter(supportFragmentManager)
        pagerAdapter.addFrag(OverviewFragment(), "Overview")
        pagerAdapter.addFrag(PlayersFragment(), "Player")
        viewPager.adapter = pagerAdapter
    }

    internal inner class TeamsPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
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