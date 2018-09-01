package com.example.abdurrahman.footballapps.ui.match.searchmatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.example.abdurrahman.footballapps.R
import com.example.abdurrahman.footballapps.api.ApiRepository
import com.example.abdurrahman.footballapps.model.Event
import com.example.abdurrahman.footballapps.ui.match.detailmatch.DetailSearchActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_search_match.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onQueryTextListener

class SearchMatchActivity : AppCompatActivity(), SearchMatchView {

    private var searchEvent: MutableList<Event> = mutableListOf()
    private lateinit var presenter: SearchMatchPresenter
    private lateinit var adapter: SearchMatchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_match)
        supportActionBar?.hide()

        searchView.onActionViewExpanded()
        val request = ApiRepository()
        val gson = Gson()
        presenter = SearchMatchPresenter(this,request, gson)
        searchView.onQueryTextListener {
            onQueryTextChange { it ->
                presenter.getSearchMatchList(it)
                true
             }
        }
        adapter = SearchMatchAdapter(searchEvent) {
            ctx.startActivity<DetailSearchActivity>("detail_search" to it)
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

    override fun showListMatch(events: List<Event>) {
        searchEvent.clear()
        searchEvent.addAll(events)
        adapter.notifyDataSetChanged()
    }
}