package com.example.abdurrahman.footballapps.ui.match.searchmatch.detailsearch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Event
import com.example.abdurrahman.footballapps.model.Teams
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailMatchPresenter
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailMatchView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class DetailSearchActivity : AppCompatActivity(), DetailMatchView {

    private lateinit var presenter: DetailMatchPresenter

    private lateinit var event: Event
    private var urlHomeBadge: String? = null
    private var urlAwayBadge: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = getString(R.string.title_detail_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        event = intent.getParcelableExtra("detail_search")

        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getHomeBadgeLogo(event.idHomeTeam)
        presenter.getAwayBadgeLogo(event.idAwayTeam)

        swipeRefresh.setColorSchemeResources(R.color.colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        swipeRefresh.onRefresh {
            presenter.getHomeBadgeLogo(event.idHomeTeam)
            presenter.getAwayBadgeLogo(event.idAwayTeam)
        }
        initEventViews(event)
    }

    private fun initEventViews(event: Event){
        val dateFormatServer = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatCustom = SimpleDateFormat("E, dd MMM yyyy")
        val date = dateFormatServer.parse(event.dateEvent)
        val strDate = dateFormatCustom.format(date)

        tvDateEvent.text = strDate
        tvHomeName.text = event.teamHomeName
        tvAwayName.text = event.teamAwayName
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> {
                onBackPressed()
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