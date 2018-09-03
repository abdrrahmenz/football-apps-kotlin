package com.example.abdurrahman.footballapps.ui.match.detailmatch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.color.colorAccent
import com.example.abdurrahman.footballapps.R.menu.detail_menu
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.model.Teams
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var presenter: DetailMatchPresenter

    private lateinit var events: Events
    private var urlHomeBadge: String? = null
    private var urlAwayBadge: String? = null
    private var menuItem: Menu? = null
//    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = getString(R.string.title_detail_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        events = intent.getParcelableExtra("detail")

//        favoriteState()

        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getHomeBadgeLogo(events.idHomeTeam)
        presenter.getAwayBadgeLogo(events.idAwayTeam)

        swipeRefresh.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        swipeRefresh.onRefresh {
            presenter.getHomeBadgeLogo(events.idHomeTeam)
            presenter.getAwayBadgeLogo(events.idAwayTeam)
        }
        initEventsViews(events)
    }

    private fun initEventsViews(events: Events) {
        val dateFormatServer = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatCustom = SimpleDateFormat("E, dd MMM yyyy")
        val date = dateFormatServer.parse(events.dateEvent)
        val strDate = dateFormatCustom.format(date)

        tvDateEvent.text = strDate
        tvAwayScore.text = events.teamAwayScore
        tvHomeScore.text = events.teamHomeScore
        tvHomeName.text = events.teamHomeName
        tvAwayName.text = events.teamAwayName
        tvHomeFormation.text = events.strHomeFormation
        tvAwayFormation.text = events.strAwayFormation
        tvHomeGoalsDetail.text = events.strHomeGoalDetails
        tvAwayGoalsDetail.text = events.strAwayGoalDetails
        tvHomeShots.text = events.intHomeShots
        tvAwayShots.text = events.intAwayShots
        tvHomeGoalKeeper.text = events.strHomeLineupGoalkeeper
        tvAwayGoalKeeper.text = events.strAwayLineupGoalkeeper
        tvHomeDefense.text = events.strHomeLineupDefense
        tvAwayDefense.text = events.strAwayLineupDefense
        tvHomeMidfield.text = events.strHomeLineupMidfield
        tvAwayMidfield.text = events.strAwayLineupMidfield
        tvHomeForward.text = events.strHomeLineupForward
        tvAwayForward.text = events.strAwayLineupForward
        tvHomeSubtituties.text = events.strHomeLineupSubstitutes
        tvAwaySubtituties.text = events.strAwayLineupSubstitutes
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.add_to_favorite -> {
                toast("add favorites :)")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showHomeTeamLogo(data: List<Teams>) {
        swipeRefresh.isRefreshing = false
        urlHomeBadge = data[0].teamBadge
        Picasso.get().load(data[0].teamBadge).placeholder(R.drawable.load).error(R.drawable.error).into(imgHomeBadge)
    }

    override fun showAwayTeamLogo(data: List<Teams>) {
        swipeRefresh.isRefreshing = false
        urlAwayBadge = data[0].teamBadge
        Picasso.get().load(data[0].teamBadge).placeholder(R.drawable.load).error(R.drawable.error).into(imgAwayBadge)
    }
}
