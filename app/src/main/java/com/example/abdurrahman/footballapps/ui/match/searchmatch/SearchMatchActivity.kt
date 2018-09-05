package com.example.abdurrahman.footballapps.ui.match.searchmatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Event
import com.example.abdurrahman.footballapps.model.Events
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailMatchActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var searchEvent: MutableList<Event> = mutableListOf()
    private var sendMatchList: MutableList<Events> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: SearchMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.hide()

        searchView.onActionViewExpanded()
        searchView.onQueryTextListener {
            onQueryTextChange { it ->
                presenter.getSearchMatchList(it)
                true
            }
        }
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this,request, gson)

        adapter = SearchMatchAdapter(searchEvent) {
            sendMatchList.clear()
                sendMatchList.add(Events(
                        0,
                        it.idEvent,
                        it.idHomeTeam,
                        it.idAwayTeam,
                        it.teamHomeName,
                        it.teamAwayName,
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        it.dateEvent))
            toast(sendMatchList[0].teamHomeName.toString() + " "+ it.teamHomeName.toString())
            ctx.startActivity<DetailMatchActivity>(
                    "idEvent" to sendMatchList[0].idEvent,
                    "idHomeTeam" to sendMatchList[0].idHomeTeam,
                    "idAwayTeam" to sendMatchList[0].idAwayTeam,
                    "teamHomeName" to sendMatchList[0].teamHomeName,
                    "teamAwayName" to sendMatchList[0].teamAwayName,
                    "dateEvent" to sendMatchList[0].dateEvent
                    )
        }
        rcvSearch.layoutManager = LinearLayoutManager(this)
        rcvSearch.adapter = adapter
    }
    override fun showLoading() {
        progressBarr.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBarr.visibility = View.GONE
    }

    override fun showListMatch(event: List<Event>) {
        searchEvent.clear()
        searchEvent.addAll(event)
        adapter.notifyDataSetChanged()
    }
}