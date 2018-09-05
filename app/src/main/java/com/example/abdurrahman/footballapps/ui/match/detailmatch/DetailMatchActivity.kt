package com.example.abdurrahman.footballapps.ui.match.detailmatch

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.Menu
import android.view.MenuItem
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.R.color.colorAccent
import com.example.abdurrahman.footballapps.R.drawable.ic_add_to_favorites
import com.example.abdurrahman.footballapps.R.drawable.ic_added_to_favorites
import com.example.abdurrahman.footballapps.R.id.add_to_favorite
import com.example.abdurrahman.footballapps.R.menu.detail_menu
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.database.database
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.model.Favorite
import com.example.abdurrahman.footballapps.model.Teams
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_match.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import java.text.SimpleDateFormat

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var presenter: DetailMatchPresenter

    private var events: Events? = null
    private var urlHomeBadge: String? = null
    private var urlAwayBadge: String? = null
    private var idEvent: String? = null
    private var idHomeTeam: String? = null
    private var idAwayTeam: String? = null
    private var teamHomeName: String? = null
    private var teamAwayName: String? = null
    private var dateEvent: String? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        supportActionBar?.title = getString(R.string.title_detail_schedule)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        events = intent.getParcelableExtra("detail")
        idEvent = intent.getStringExtra("idEvent")
        idHomeTeam = intent.getStringExtra("idHomeTeam")
        idAwayTeam = intent.getStringExtra("idAwayTeam")
        teamHomeName = intent.getStringExtra("teamHomeName")
        teamAwayName = intent.getStringExtra("teamAwayName")
        dateEvent = intent.getStringExtra("dateEvent")

        favoriteState()
        favoriteStateSearch()

        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailMatchPresenter(this, request, gson)
        presenter.getHomeBadgeLogo(events?.idHomeTeam ?: idHomeTeam)
        presenter.getAwayBadgeLogo(events?.idAwayTeam ?: idAwayTeam)

        swipeRefresh.setColorSchemeResources(colorAccent,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light)
        swipeRefresh.onRefresh {
            presenter.getHomeBadgeLogo(events?.idHomeTeam ?: idHomeTeam)
            presenter.getAwayBadgeLogo(events?.idAwayTeam ?: idAwayTeam)
        }
        val dateFormatServer = SimpleDateFormat("yyyy-MM-dd")
        val dateFormatCustom = SimpleDateFormat("E, dd MMM yyyy")
        val date = dateFormatServer.parse(events?.dateEvent ?: dateEvent)
        val strDate = dateFormatCustom.format(date)

        tvDateEvent.text = strDate
        tvAwayScore.text = events?.teamAwayScore
        tvHomeScore.text = events?.teamHomeScore
        tvHomeName.text = events?.teamHomeName ?: teamHomeName
        tvAwayName.text = events?.teamAwayName ?: teamAwayName
        tvHomeFormation.text = events?.strHomeFormation
        tvAwayFormation.text = events?.strAwayFormation
        tvHomeGoalsDetail.text = events?.strHomeGoalDetails
        tvAwayGoalsDetail.text = events?.strAwayGoalDetails
        tvHomeShots.text = events?.intHomeShots
        tvAwayShots.text = events?.intAwayShots
        tvHomeGoalKeeper.text = events?.strHomeLineupGoalkeeper
        tvAwayGoalKeeper.text = events?.strAwayLineupGoalkeeper
        tvHomeDefense.text = events?.strHomeLineupDefense
        tvAwayDefense.text = events?.strAwayLineupDefense
        tvHomeMidfield.text = events?.strHomeLineupMidfield
        tvAwayMidfield.text = events?.strAwayLineupMidfield
        tvHomeForward.text = events?.strHomeLineupForward
        tvAwayForward.text = events?.strAwayLineupForward
        tvHomeSubtituties.text = events?.strHomeLineupSubstitutes
        tvAwaySubtituties.text = events?.strAwayLineupSubstitutes
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_favorites)
    }

    private fun favoriteState(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE_MATCH)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to events?.idEvent.toString())
            val favorite = result.parseList(classParser<Events>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite() {
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE_MATCH,
                        Favorite.EVENT_ID to events?.idEvent,
                        Favorite.TEAM_HOME_ID to events?.idHomeTeam,
                        Favorite.TEAM_AWAY_ID to events?.idAwayTeam,
                        Favorite.TEAM_HOME_NAME to events?.teamHomeName,
                        Favorite.TEAM_AWAY_NAME to events?.teamAwayName,
                        Favorite.TEAM_HOME_SCORE to events?.teamHomeScore,
                        Favorite.TEAM_AWAY_SCORE to events?.teamAwayScore,
                        Favorite.TEAM_HOME_GOAL to events?.strHomeGoalDetails,
                        Favorite.TEAM_AWAY_GOAL to events?.strAwayGoalDetails,
                        Favorite.TEAM_HOME_SHOTS to events?.intHomeShots,
                        Favorite.TEAM_AWAY_SHOTS to events?.intAwayShots,
                        Favorite.TEAM_HOME_GOALKEEPER to events?.strHomeLineupGoalkeeper,
                        Favorite.TEAM_AWAY_GOALKEEPER to events?.strAwayLineupGoalkeeper,
                        Favorite.TEAM_HOME_DEFENSE to events?.strHomeLineupDefense,
                        Favorite.TEAM_AWAY_DEFENSE to events?.strAwayLineupDefense,
                        Favorite.TEAM_HOME_MIDFIELD to events?.strHomeLineupMidfield,
                        Favorite.TEAM_AWAY_MIDFIELD to events?.strAwayLineupMidfield,
                        Favorite.TEAM_HOME_FORWARD to events?.strHomeLineupForward,
                        Favorite.TEAM_AWAY_FORWARD to events?.strAwayLineupForward,
                        Favorite.TEAM_HOME_SUBTITUTIS to events?.strHomeLineupSubstitutes,
                        Favorite.TEAM_AWAY_SUBTITUTIS to events?.strAwayLineupSubstitutes,
                        Favorite.TEAM_HOME_FORMATION to events?.strHomeFormation,
                        Favorite.TEAM_AWAY_FORMATION to events?.strAwayFormation,
                        Favorite.TEAM_BADGE to events?.teamBadge,
                        Favorite.TEAM_DATE_EVENT to events?.dateEvent)
            }
            snackbar(swipeRefresh, getString(R.string.msg_snackbar_add_fav)).show()
        }catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE_MATCH,"(EVENT_ID = {id})",
                        "id" to events?.idEvent.toString())
            }
            snackbar(swipeRefresh, getString(R.string.msg_snackbar_remove_fav)).show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun favoriteStateSearch(){
        database.use {
            val result = select(Favorite.TABLE_FAVORITE_MATCH)
                    .whereArgs("(EVENT_ID = {id})",
                            "id" to idEvent.toString())
            val favorite = result.parseList(classParser<Events>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavoriteSearch(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE_MATCH,
                        Favorite.EVENT_ID to idEvent,
                        Favorite.TEAM_HOME_ID to idHomeTeam,
                        Favorite.TEAM_AWAY_ID to idAwayTeam,
                        Favorite.TEAM_HOME_NAME to teamHomeName,
                        Favorite.TEAM_AWAY_NAME to teamAwayName,
                        Favorite.TEAM_HOME_SCORE to "",
                        Favorite.TEAM_AWAY_SCORE to "",
                        Favorite.TEAM_HOME_GOAL to "",
                        Favorite.TEAM_AWAY_GOAL to "",
                        Favorite.TEAM_HOME_SHOTS to "",
                        Favorite.TEAM_AWAY_SHOTS to "",
                        Favorite.TEAM_HOME_GOALKEEPER to "",
                        Favorite.TEAM_AWAY_GOALKEEPER to "",
                        Favorite.TEAM_HOME_DEFENSE to "",
                        Favorite.TEAM_AWAY_DEFENSE to "",
                        Favorite.TEAM_HOME_MIDFIELD to "",
                        Favorite.TEAM_AWAY_MIDFIELD to "",
                        Favorite.TEAM_HOME_FORWARD to "",
                        Favorite.TEAM_AWAY_FORWARD to "",
                        Favorite.TEAM_HOME_SUBTITUTIS to "",
                        Favorite.TEAM_AWAY_SUBTITUTIS to "",
                        Favorite.TEAM_HOME_FORMATION to "",
                        Favorite.TEAM_AWAY_FORMATION to "",
                        Favorite.TEAM_BADGE to "",
                        Favorite.TEAM_DATE_EVENT to dateEvent)
            }
            snackbar(swipeRefresh, getString(R.string.msg_snackbar_add_fav)).show()
        }catch (e: SQLiteConstraintException) {
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    private fun removeFromFavoriteSearch(){
        try {
            database.use {
                delete(Favorite.TABLE_FAVORITE_MATCH,"(EVENT_ID = {id})",
                        "id" to idEvent.toString())
            }
            snackbar(swipeRefresh, getString(R.string.msg_snackbar_remove_fav)).show()
        } catch (e: SQLiteConstraintException){
            snackbar(swipeRefresh, e.localizedMessage).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
            menuInflater.inflate(detail_menu, menu)
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
                if (idEvent.isNullOrBlank()) {
                    if (isFavorite) removeFromFavorite() else addToFavorite()
                    isFavorite = !isFavorite
                    setFavorite()
                }else{
                    if (isFavorite) removeFromFavoriteSearch() else addToFavoriteSearch()
                    isFavorite = !isFavorite
                    setFavorite()
                }

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
